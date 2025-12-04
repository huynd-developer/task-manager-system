package org.example.shopapp.controller.admin;

import org.example.shopapp.service.OrderService;
import org.example.shopapp.service.ProductService;
import org.example.shopapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public AdminController(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalProducts", productService.getTotalProducts());
            stats.put("totalUsers", userService.getTotalUsers());
            stats.put("totalOrders", orderService.getTotalOrders());
            stats.put("totalRevenue", orderService.getTotalRevenue());

            model.addAttribute("stats", stats);
            model.addAttribute("recentOrders", orderService.getRecentOrders(5));
            model.addAttribute("lowStockProducts", productService.getLowStockProducts(3));

            return "admin/dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tải dashboard: " + e.getMessage());
            return "admin/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return adminDashboard(model);
    }

}