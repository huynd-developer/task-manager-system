package org.example.shopapp.controller;

import org.example.shopapp.entity.CartItem;
import org.example.shopapp.entity.Order;
import org.example.shopapp.entity.User;
import org.example.shopapp.service.CartService;
import org.example.shopapp.service.OrderService;
import org.example.shopapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    public OrderController(OrderService orderService, UserService userService, CartService cartService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String viewOrders(Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElseThrow();

            List<Order> orders = orderService.getOrdersByUserId(user.getId());
            model.addAttribute("orders", orders);
            return "orders";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tải đơn hàng: " + e.getMessage());
            return "orders";
        }
    }

    @PostMapping("/create")
    public String createOrder(Authentication authentication,
                              @RequestParam String shippingAddress,
                              RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElseThrow();

            List<CartItem> cartItems = cartService.getCartByUserId(user.getId());
            if (cartItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống!");
                return "redirect:/cart";
            }

            Order order = orderService.createOrderFromCart(user, shippingAddress);
            cartService.clearCart(user.getId());

            redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công! Mã đơn: #" + order.getId());
            return "redirect:/orders";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            System.out.println("=== DEBUG: CANCEL ORDER #" + id + " ===");

            if (authentication == null || !authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập!");
                return "redirect:/login";
            }

            String username = authentication.getName();
            System.out.println("Username: " + username);

            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            System.out.println("User ID: " + user.getId());

            Order order = orderService.getOrderById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng #" + id));

            System.out.println("Order User ID: " + order.getUser().getId());
            System.out.println("Order Status: " + order.getStatus());

            if (!order.getUser().getId().equals(user.getId())) {
                redirectAttributes.addFlashAttribute("error", "Bạn không có quyền hủy đơn hàng này!");
                return "redirect:/orders";
            }

            if (!order.getStatus().equals("PENDING") && !order.getStatus().equals("CONFIRMED")) {
                redirectAttributes.addFlashAttribute("error",
                        "Chỉ có thể hủy đơn hàng khi đang ở trạng thái 'Chờ xác nhận' hoặc 'Đã xác nhận'");
                return "redirect:/orders";
            }

            orderService.cancelOrder(id);

            redirectAttributes.addFlashAttribute("message",
                    "Đã hủy đơn hàng #" + id + " thành công!");

        } catch (Exception e) {
            System.err.println("ERROR cancelling order: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Lỗi khi hủy đơn hàng: " + e.getMessage());
        }

        return "redirect:/orders";
    }
}