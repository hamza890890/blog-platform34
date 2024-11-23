package com.example.controller;

import com.example.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalViewController {

    @GetMapping("/personal-view")
    public String personalView(Model model) {
        // Create or fetch the Post object you want to display
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Sample Post");
        // Add the post object to the model
        model.addAttribute("post", post);

        return "personal-view";  // This corresponds to the personal-view.html template
    }
}

