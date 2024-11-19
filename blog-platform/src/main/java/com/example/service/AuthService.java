package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder;

    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /*public boolean loginUser(User user) {
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

