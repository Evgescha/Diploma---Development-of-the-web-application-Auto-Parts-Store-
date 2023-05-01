package com.hescha.autochapterstore.controller;

import com.hescha.autochapterstore.model.User;
import com.hescha.autochapterstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginAndRegistrationController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }

    @PostMapping("/registration")
    public String registrationPost(User user, Model model) {
        boolean success = userService.registerNew(user);
        String response = success
                ? "Success"
                : "Registration failed. Try again later";
        model.addAttribute("success", response);
        return "sign-in";
    }
}
