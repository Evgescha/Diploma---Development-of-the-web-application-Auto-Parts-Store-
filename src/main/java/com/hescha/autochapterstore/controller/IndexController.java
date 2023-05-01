package com.hescha.autochapterstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping(path = {"/index", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/shop")
    public String shop() {
        return "shop";
    }

    @GetMapping("/single-product")
    public String singleproduct() {
        return "single-product";
    }
}
