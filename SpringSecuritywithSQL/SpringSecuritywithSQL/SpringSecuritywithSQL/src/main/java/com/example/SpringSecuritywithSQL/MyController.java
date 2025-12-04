package com.example.SpringSecuritywithSQL;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
    @RequestMapping
    public String home(Model model){
        model.addAttribute("message", "@/=>home()");
        return "page";
    }
    @RequestMapping("/poly/url/0")
    public String poly(Model model){
        model.addAttribute("message", "@/poly/url0=>method()");
        return "page";
    }
    @RequestMapping("/poly/url/1")
    public String poly1(Model model){
        model.addAttribute("message", "@/poly/url1=>method()");
        return "page";
    }

    @RequestMapping("/poly/url/2")
    public String poly2(Model model){
        model.addAttribute("message", "@/poly/url2=>method()");
        return "page";
    }
    @RequestMapping("/poly/url/3")
    public String poly3(Model model){
        model.addAttribute("message", "@/poly/url3=>method()");
        return "page";
    }
    @RequestMapping("/poly/url/4")
    public String poly4(Model model){
        model.addAttribute("message", "@/poly/url4=>method()");
        return "page";
    }

}
