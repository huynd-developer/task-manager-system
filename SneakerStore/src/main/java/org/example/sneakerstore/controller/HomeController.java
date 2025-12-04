package org.example.sneakerstore.controller;
import org.example.sneakerstore.entity.Product;
import org.example.sneakerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> featuredProducts = productService.getAllProducts();
        model.addAttribute("featuredProducts", featuredProducts);
        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "home/contact";
    }
}
