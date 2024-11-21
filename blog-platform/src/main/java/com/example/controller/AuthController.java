package com.example.controller;

import com.example.model.User;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
    /* This method is to handle errors to avoid a whitelabel error
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUserEndpoint(@RequestBody User user, @RequestParam String confirmPassword) {
        Map<String, String> response = registerUser(user, confirmPassword);
        if ("error".equals(response.get("status"))) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    */
   /* @PostMapping("/login") This method is mean to redirect the user to the personal feed if login was successful and to stay on the page and display a message if login was unsuccessful
    public String loginUser(User user, Model model) {
        System.out.println("Logging in user: " + user.getUsername());
        if (authService.loginUser(user)) {
            return "redirect:/personal-feed";
        } else {
            model.addAttribute("loginError", "User does not exist, check credentials. ");
            return "login";
        }
    }

    */
}