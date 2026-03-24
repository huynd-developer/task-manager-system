package org.example.shopapp.controller.admin;

import org.example.shopapp.entity.Order;
import org.example.shopapp.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrderManagement(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String viewOrderDetail(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "admin/order-detail";
    }

    @PostMapping("/{id}/update-status")
    public String updateOrderStatus(@PathVariable("id") Long id,
                                    @RequestParam String status) {
        try {
            orderService.updateOrderStatus(id, status);
            return "redirect:/admin/orders?message=Order status updated successfully";
        } catch (Exception e) {
            return "redirect:/admin/orders?error=Failed to update order status";
        }
    }
}