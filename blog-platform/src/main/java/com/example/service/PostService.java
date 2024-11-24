package com.example.service;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> findAllPublishedPosts() {
        return postRepository.findAllByPublished(true);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void createPost(Post post, String username) {
        User user = userRepository.findByUsername(username);
        post.setAuthor(user);
        postRepository.save(post);
    }
    public void createPost(Post post, MultipartFile[] images, String username) {
        User user = userRepository.findByUsername(username);
        post.setAuthor(user);
        // Save a post

        // Save images and generate file paths
        StringBuilder imagePaths = new StringBuilder();
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        String filePath = saveImageToDisk(image);
                        imagePaths.append(filePath).append(";");
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save image", e);
                    }
                }
            }
        }
        post.setImagePaths(imagePaths.toString());
        postRepository.save(post);
    }

    private String saveImageToDisk(MultipartFile image) throws IOException {
        String directory = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(directory + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return path.toString();
    }
    // Save a post
    public Post save(Post post) {
        return postRepository.save(post);
    }
    public void updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setPublished(updatedPost.isPublished());
            postRepository.save(post);
        }
    }
    public List<Post> findPublicPosts() {
        return postRepository.findAllByPublishedAndPrivacy(true, "PUBLIC");
    }

    public List<Post> findPrivatePosts(String username) {
        User user = userRepository.findByUsername(username);
        return postRepository.findAllByPublishedAndPrivacy(true, "PRIVATE")
                .stream()
                .filter(post -> post.getAuthor().equals(user) || user.getFriends().contains(post.getAuthor()))
                .collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

