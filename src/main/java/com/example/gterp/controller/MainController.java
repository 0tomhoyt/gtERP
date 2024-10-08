package com.example.gterp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // 返回模板名为 index.html
    }
}
