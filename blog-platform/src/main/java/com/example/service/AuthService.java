package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
    //  private PasswordEncoder;

    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean loginUser(User user) {
        // Retrieve the user from the repository based on the username
        User existingUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists and the passwords match
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return true; // Login successful
        }

        return false; // Login failed
    }
    public boolean isUsernameTaken(String username) {
        // Example logic: Check the user repository or database
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailInUse(String email) {
        // Example logic: Check the user repository or database
        return userRepository.existsByEmail(email);
    }


    /*
    public Map<String, String> registerUser(User user, String confirmPassword) {
        Map<String, String> response = new HashMap<>();

        // Check if the password and confirmPassword match
        if (!user.getPassword().equals(confirmPassword)) {
            response.put("status", "error");
            response.put("message", "Passwords do not match.");
            return response; // Return error message
        }

        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()) != null) {
            response.put("status", "error");
            response.put("message", "Username is already taken.");
            return response; // Return error message
        }

        // Check if the email is already taken
        if (userRepository.findByEmail(user.getEmail()) != null) {
            response.put("status", "error");
            response.put("message", "Email is already registered.");
            return response; // Return error message
        }

        // Check if the password is already used (optional)
        if (userRepository.findByPassword(passwordEncoder.encode(user.getPassword())) != null) {
            response.put("status", "error");
            response.put("message", "Password is already in use.");
            return response; // Return error message
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        userRepository.save(user);
        response.put("status", "success");
        response.put("message", "User registered successfully!");
        return response; // Return success message
    }

    public boolean loginUser(User user) {
        // Retrieve the user from the repository based on the username
        User existingUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists and the password matches
        String passwordEncoder = "";
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return true; // Login successful
        }

        return false; // Login failed
    }
    */
}

