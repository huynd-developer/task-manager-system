package org.example.sneakerstoremanagement.controller;

import org.example.sneakerstoremanagement.entity.Sneaker;
import org.example.sneakerstoremanagement.entity.User;
import org.example.sneakerstoremanagement.repository.UserRepository;
import org.example.sneakerstoremanagement.service.SneakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sneakers")
public class SneakerController {

    @Autowired
    private SneakerService sneakerService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String listSneakers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        List<Sneaker> sneakers = sneakerService.getAll();
        model.addAttribute("sneakers", sneakers);
        model.addAttribute("user", user);
        return "sneaker/list"; // Thymeleaf template
    }

    @GetMapping("/add")
    public String addSneakerForm(Model model) {
        model.addAttribute("sneaker", new Sneaker());
        return "sneaker/add";
    }

    @PostMapping("/add")
    public String addSneaker(@ModelAttribute Sneaker sneaker, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        sneaker.setUser(user);
        sneakerService.save(sneaker);
        return "redirect:/sneakers";
    }

    @GetMapping("/edit/{id}")
    public String editSneakerForm(@PathVariable Integer id, Model model) {
        Sneaker sneaker = sneakerService.getById(id).orElse(null);
        model.addAttribute("sneaker", sneaker);
        return "sneaker/edit";
    }

    @PostMapping("/edit")
    public String editSneaker(@ModelAttribute Sneaker sneaker) {
        sneakerService.save(sneaker);
        return "redirect:/sneakers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSneaker(@PathVariable Integer id) {
        sneakerService.deleteById(id);
        return "redirect:/sneakers";
    }

    @GetMapping("/search")
    public String searchSneakers(@RequestParam String keyword, Model model) {
        List<Sneaker> sneakers = sneakerService.searchByName(keyword);
        model.addAttribute("sneakers", sneakers);
        return "sneaker/list";
    }
}
