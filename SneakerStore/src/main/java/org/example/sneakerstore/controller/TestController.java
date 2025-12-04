package org.example.sneakerstore.controller;
import org.example.sneakerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public String testPage(Model model) {
        try {
            var products = productService.getAllProducts();
            model.addAttribute("products", products);
            model.addAttribute("message", "Kết nối database thành công!");
            return "test";
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi kết nối database: " + e.getMessage());
            return "test";
        }
    }
}
