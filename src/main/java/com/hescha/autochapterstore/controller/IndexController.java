package com.hescha.autochapterstore.controller;

import com.hescha.autochapterstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {
    private final ProductService service;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("list", service.readAllNotDeleted());
        return "index";
    }

    @GetMapping("/about")
    public String page404() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/error")
    public String error() {
        return "404";
    }

}
