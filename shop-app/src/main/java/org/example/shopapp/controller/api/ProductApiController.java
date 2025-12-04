package org.example.shopapp.controller.api;

import org.example.shopapp.entity.Product;
import org.example.shopapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            System.out.println("=== API: GET PRODUCT BY ID ===");
            System.out.println("Product ID: " + id);

            Product product = productService.getProductById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));

            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("name", product.getName());
            productMap.put("description", product.getDescription());
            productMap.put("price", product.getPrice());
            productMap.put("stockQuantity", product.getStockQuantity());
            productMap.put("imageUrl", product.getImageUrl());

            System.out.println("Product found: " + product.getName());
            return ResponseEntity.ok(productMap);

        } catch (Exception e) {
            System.err.println("Error in getProductById: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Lỗi khi tải sản phẩm: " + e.getMessage())
            );
        }
    }
}