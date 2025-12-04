package org.example.shopapp.controller;

import org.example.shopapp.entity.Product;
import org.example.shopapp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {

        List<Product> allProducts = productService.getAllProducts();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase().trim();
            allProducts = allProducts.stream()
                    .filter(p ->
                            p.getName().toLowerCase().contains(lowerKeyword) ||
                                    (p.getDescription() != null &&
                                            p.getDescription().toLowerCase().contains(lowerKeyword)))
                    .collect(Collectors.toList());
        }

        if (sort != null) {
            switch (sort) {
                case "price_asc":
                    allProducts = allProducts.stream()
                            .sorted(Comparator.comparing(Product::getPrice))
                            .collect(Collectors.toList());
                    break;
                case "price_desc":
                    allProducts = allProducts.stream()
                            .sorted(Comparator.comparing(Product::getPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                case "name_asc":
                    allProducts = allProducts.stream()
                            .sorted(Comparator.comparing(Product::getName))
                            .collect(Collectors.toList());
                    break;
            }
        }

        int totalItems = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalItems);

        List<Product> products = allProducts.subList(startIndex, endIndex);

        System.out.println("=== DEBUG PAGINATION ===");
        System.out.println("Total products: " + totalItems);
        System.out.println("Page size: " + size);
        System.out.println("Total pages: " + totalPages);
        System.out.println("Current page: " + page);
        System.out.println("Products on this page: " + products.size());
        System.out.println("=== END DEBUG ===");

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startItem", totalItems > 0 ? startIndex + 1 : 0);
        model.addAttribute("endItem", Math.min(endIndex, totalItems));

        return "product-list";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product-detail";
        }
        return "redirect:/products";
    }
}