package org.example.shopapp.controller.api;

import org.example.shopapp.entity.CartItem;
import org.example.shopapp.entity.User;
import org.example.shopapp.service.CartService;
import org.example.shopapp.service.ProductService;
import org.example.shopapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public CartApiController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        try {
            System.out.println("=== API: GET CART ===");
            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("User not authenticated");
                return ResponseEntity.status(401).body(
                        Map.of("error", "Vui lòng đăng nhập để xem giỏ hàng")
                );
            }

            String username = authentication.getName();
            System.out.println("Username: " + username);

            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            List<CartItem> cartItems = cartService.getCartByUserId(user.getId());
            System.out.println("Cart items count: " + cartItems.size());

            List<Map<String, Object>> cartResponse = cartItems.stream()
                    .map(item -> {
                        Map<String, Object> itemMap = new HashMap<>();
                        itemMap.put("id", item.getId());
                        itemMap.put("quantity", item.getQuantity());

                        Map<String, Object> productMap = new HashMap<>();
                        productMap.put("id", item.getProduct().getId());
                        productMap.put("name", item.getProduct().getName());
                        productMap.put("price", item.getProduct().getPrice());
                        productMap.put("stockQuantity", item.getProduct().getStockQuantity());
                        productMap.put("imageUrl", item.getProduct().getImageUrl());

                        itemMap.put("product", productMap);
                        return itemMap;
                    })
                    .toList();

            System.out.println("Cart API response successful");
            return ResponseEntity.ok(cartResponse);

        } catch (Exception e) {
            System.err.println("Error in getCartApi: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Lỗi khi tải giỏ hàng: " + e.getMessage())
            );
        }
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication) {
        try {
            System.out.println("=== API: ADD TO CART ===");
            System.out.println("Product ID: " + productId);
            System.out.println("Quantity: " + quantity);

            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("User not authenticated");
                return ResponseEntity.status(401).body(
                        Map.of("error", "Vui lòng đăng nhập để thêm vào giỏ hàng")
                );
            }

            String username = authentication.getName();
            System.out.println("Username: " + username);

            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            var productOpt = productService.getProductById(productId);
            if (productOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Không tìm thấy sản phẩm với ID: " + productId)
                );
            }

            cartService.addToCart(user, productOpt.get(), quantity);

            System.out.println("Product added to cart successfully");

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Đã thêm sản phẩm vào giỏ hàng!"
            ));

        } catch (Exception e) {
            System.err.println("Error in addToCartApi: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long productId,
            Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body(
                        Map.of("error", "Vui lòng đăng nhập")
                );
            }

            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            cartService.removeFromCart(user.getId(), productId);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Đã xóa sản phẩm khỏi giỏ hàng"
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Lỗi khi xóa sản phẩm: " + e.getMessage())
            );
        }
    }
}