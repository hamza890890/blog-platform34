package com.example.controller;

import com.example.model.User;
import com.example.service.AuthService;
import com.example.service.EmailService; // Import your email service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService; // Inject the email service

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        System.out.println("Registering user: " + user.getUsername());

        if (authService.registerUser(user)) {
            // Send a welcome email
            String subject = "Welcome to Blog Platform!";
            String message = String.format(
                    "Hi %s,\n\nWelcome to Blog Platform! We're excited to have you on board.\n\nHappy blogging!\n- Blog Platform Team",
                    user.getUsername()
            );
            emailService.sendEmail(user.getEmail(), subject, message);

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
