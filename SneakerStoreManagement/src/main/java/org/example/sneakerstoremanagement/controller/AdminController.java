package org.example.sneakerstoremanagement.controller;
import org.example.sneakerstoremanagement.entity.Sneaker;
import org.example.sneakerstoremanagement.entity.User;
import org.example.sneakerstoremanagement.repository.SneakerRepository;
import org.example.sneakerstoremanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SneakerRepository sneakerRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "admin/users"; // Thymeleaf template admin/users.html
    }

    @GetMapping("/sneakers")
    public String listSneakers(Model model) {
        List<Sneaker> sneakers = sneakerRepo.findAll();
        model.addAttribute("sneakers", sneakers);
        return "admin/sneakers"; // Thymeleaf template admin/sneakers.html
    }

    @GetMapping("/sneakers/delete/{id}")
    public String deleteSneaker(@PathVariable Integer id) {
        sneakerRepo.deleteById(id);
        return "redirect:/admin/sneakers";
    }
}
