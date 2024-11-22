package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.model.User;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String confirmPassword,
            User user,
            Model model
    ) {
        logger.info("Attempting to register user: {}", user.getUsername());

        boolean hasErrors = false;

        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            logger.error("Registration failed for user {}: Passwords do not match.", user.getUsername());
            model.addAttribute("passwordError", "Passwords do not match.");
            hasErrors = true;
        }

        // Check if the username is already taken
        if (authService.isUsernameTaken(user.getUsername())) {
            logger.error("Registration failed for user {}: Username is already taken.", user.getUsername());
            model.addAttribute("usernameError", "Username is already taken. Try a different Username.");
            hasErrors = true;
        }

        // Check if the email is already in use
        if (authService.isEmailInUse(user.getEmail())) {
            logger.error("Registration failed for user {}: Email is already in use.", user.getUsername());
            model.addAttribute("emailError", "Email is already in use. Login or use a different email.");
            hasErrors = true;
        }

        // If there are any errors, re-display the form with the user's input
        if (hasErrors) {
            model.addAttribute("user", user); // Add the user back to the model
            return "register";
        }

        // Attempt to register the user
        if (authService.registerUser(user)) {
            logger.info("User {} registered successfully.", user.getUsername());
            return "redirect:/login";
        } else {
            logger.error("Registration failed for user {}: Unknown error.", user.getUsername());
            model.addAttribute("registrationError", "An unknown error occurred. Please try again.");
            model.addAttribute("user", user); // Add the user back to the model
            return "register";
        }
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("Displaying login form.");
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(User user, Model model) {
        logger.info("Attempting login for user: {}", user.getUsername());
        if (authService.loginUser(user)) {
            logger.info("User {} logged in successfully.", user.getUsername());
            return "redirect:/personal-feed";
        } else {
            logger.error("Login failed for user {}: Invalid credentials.", user.getUsername());
            model.addAttribute("loginError", "User does not exist, check credentials.");
            return "login";
        }
    }
}
