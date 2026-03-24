package org.example.shopapp.controller.admin;

import org.example.shopapp.entity.Product;
import org.example.shopapp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductManagement(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "admin/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOpt = productService.getProductById(id);

        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("isEdit", true);
            return "admin/products";
        } else {
            return "redirect:/admin/products?error=Product not found";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute Product product,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.updateProduct(id, product, imageFile);
            redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {
        try {
            productService.saveProduct(product, imageFile);
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }
}