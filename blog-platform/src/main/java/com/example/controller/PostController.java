package com.example.controller;

import com.example.model.Post;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("posts", postService.findAllPublishedPosts());
        return "index";
    }
    /*
    @GetMapping("/timeline")
    public String showTimeline(Model model) {
        // Get posts sorted by creation date, from newest to oldest

        List<Post> posts = PostRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("posts", posts);
        return "timeline"; // The Thymeleaf template name
    }

     */
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "view-post";
    }

    @GetMapping("/post/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

//    @PostMapping("/post/new")
//    public String createPost(Post post, Authentication authentication) {
//        postService.createPost(post, authentication.getName());
//        return "redirect:/";
//    }

    @GetMapping("/post/edit/{id}")
    public String showEditPostForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Post post) {
        postService.updatePost(id, post);
        return "redirect:/";
    }

    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }
}