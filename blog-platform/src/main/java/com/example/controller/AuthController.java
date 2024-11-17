package com.example.controller;

import com.example.model.User;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        System.out.println("Registering user: " + user.getUsername());
        if (authService.registerUser(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("registrationError", "User already exists");
            return "register";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("login");
        return "login";
    }
}