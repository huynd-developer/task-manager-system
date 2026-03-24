package org.example.sneakerstoremanagement.controller;
import org.example.sneakerstoremanagement.entity.*;
import org.example.sneakerstoremanagement.repository.SneakerRepository;
import org.example.sneakerstoremanagement.repository.UserRepository;
import org.example.sneakerstoremanagement.service.CartService;
import org.example.sneakerstoremanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SneakerRepository sneakerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        List<CartItem> cartItems = cartService.getCartItemsByUser(user.getId());
        model.addAttribute("cartItems", cartItems);
        return "cart/list";
    }

    @PostMapping("/add/{sneakerId}")
    public String addToCart(@PathVariable Integer sneakerId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        Sneaker sneaker = sneakerRepo.findById(sneakerId).orElse(null);
        CartItem item = new CartItem();
        item.setSneaker(sneaker);
        item.setUser(user);
        item.setQuantity(1);
        cartService.addToCart(item);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        List<CartItem> cartItems = cartService.getCartItemsByUser(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setSneaker(ci.getSneaker());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getSneaker().getPrice() * ci.getQuantity());
            orderItems.add(oi);
            total += oi.getPrice();
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        orderService.createOrder(order);

        cartService.clearCartByUser(user.getId());
        return "redirect:/orders";
    }
}

