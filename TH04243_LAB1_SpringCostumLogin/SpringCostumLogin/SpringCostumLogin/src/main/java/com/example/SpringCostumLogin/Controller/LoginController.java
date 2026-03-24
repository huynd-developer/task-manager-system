package com.example.SpringCostumLogin.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/auth/{action}")
    public String login(Model model, @PathVariable("action") String action){
        return switch (action){
            case "login" -> {
                model.addAttribute("message", "Đăng nhập");
                yield "/auth/login";
            }

            // 1. XÓA CASE "SUCCESS" ĐI. Nó không cần thiết và gây lỗi.
            case "success" -> {
                model.addAttribute("message","Sai thông tin đăng nhập");
                yield "/auth/success";
            }
            case "failure" -> {
                model.addAttribute("message","Sai thông tin đăng nhập");
                yield "/auth/failure";
            }
            case "exit" -> {
                model.addAttribute("message", "Đăng xuất thành công");
                yield "/auth/exit";
            }
            default -> "/auth/login";
        };
    }
}