package org.example.restapiintroduction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {
    @RequestMapping("/test-axios")
    public String testAxios() {
        return "/student/test-axios";
    }


}
