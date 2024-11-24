package com.example.controller;

import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
import com.example.service.PostService;
import com.example.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    @Autowired
    private PostService postService;
    private UserService userService;
    private PostRepository postRepository;
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Show public posts on the homepage
        model.addAttribute("posts", postService.findPublicPosts());
        return "index";
    }

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

    @GetMapping("/post/edit/{id}")
    public String showEditPostForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/post/new")
    public String createPost(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("images") MultipartFile[] images,
                             @RequestParam("privacy") String privacy,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {

        try {
            // Create a new post object and set values
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setPrivacy(privacy);

            // Handle images, catch IOException
            List<byte[]> imageBytes = new ArrayList<>();
            for (MultipartFile file : images) {
                try {
                    imageBytes.add(file.getBytes());
                } catch (IOException e) {
                    // Handle the IOException for each image individually
                    redirectAttributes.addFlashAttribute("message", "Error processing image: " + file.getOriginalFilename());
                    return "redirect:/post/new"; // Stay on the create post page if image processing fails
                }
            }
            post.setImages(imageBytes);

            // Set the author of the post
            post.setAuthor(getLoggedInUser(principal));

            // Save the post to the database
            postService.save(post);

            // Add success message
            redirectAttributes.addFlashAttribute("message", "Post published successfully!");

            // Redirect to personal feed (or homepage)
            return "redirect:/feed"; // Change to your feed page URL

        } catch (Exception e) {
            // Handle general failure
            redirectAttributes.addFlashAttribute("message", "Failed to publish post. Please try again.");
            return "redirect:/post/new"; // Stay on the create post page
        }
    }

    private User getLoggedInUser(Principal principal) {
        // Get the logged-in user from the principal
        return userService.findByUsername(principal.getName());
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

    @GetMapping("/feed")
    public String viewPersonalFeed(@RequestParam("username") String username, Model model) {
        model.addAttribute("posts", postService.findPrivatePosts(username));
        return "feed";
    }

}
