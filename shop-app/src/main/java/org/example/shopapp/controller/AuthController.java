package org.example.shopapp.controller;

import org.example.shopapp.entity.User;
import org.example.shopapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username đã tồn tại");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login?registered";
    }
}
