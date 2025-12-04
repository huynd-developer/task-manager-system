package org.example.sneakerstoremanagement.controller;

import org.example.sneakerstoremanagement.entity.Order;
import org.example.sneakerstoremanagement.entity.User;
import org.example.sneakerstoremanagement.repository.UserRepository;
import org.example.sneakerstoremanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String listOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        List<Order> orders = orderService.getOrdersByUser(user.getId());
        model.addAttribute("orders", orders);
        return "orders/list";
    }
}


