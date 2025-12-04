package org.example.shopapp.controller;

import org.example.shopapp.entity.User;
import org.example.shopapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profilePage(Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            model.addAttribute("user", user);
            return "profile";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải thông tin: " + e.getMessage());
            return "profile";
        }
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User userDetails,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User currentUser = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            currentUser.setEmail(userDetails.getEmail());
            currentUser.setFullName(userDetails.getFullName());
            currentUser.setPhone(userDetails.getPhone());
            currentUser.setAddress(userDetails.getAddress());

            userService.updateUser(currentUser.getId(), currentUser);

            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
            return "redirect:/profile";
        }
    }

    @GetMapping("/change-password")
    public String changePasswordForm(Model model) {
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordForm") PasswordChangeForm form,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!userService.checkPassword(form.getCurrentPassword(), user.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
                return "redirect:/profile/change-password";
            }

            if (!form.getNewPassword().equals(form.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không trùng khớp!");
                return "redirect:/profile/change-password";
            }

            userService.changePassword(user.getId(), form.getNewPassword());

            redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đổi mật khẩu: " + e.getMessage());
            return "redirect:/profile/change-password";
        }
    }
}