package org.example.shopapp.controller.admin;

import org.example.shopapp.entity.User;
import org.example.shopapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserManagement(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "admin/user-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute User userDetails) {
        try {
            userService.updateUser(id, userDetails);
            return "redirect:/admin/users?message=User updated successfully";
        } catch (Exception e) {
            return "redirect:/admin/users?error=Failed to update user";
        }
    }

    @PostMapping("/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable("id") Long id) {
        try {
            userService.toggleUserStatus(id);
            return "redirect:/admin/users?message=User status updated successfully";
        } catch (Exception e) {
            return "redirect:/admin/users?error=Failed to update user status";
        }
    }
}