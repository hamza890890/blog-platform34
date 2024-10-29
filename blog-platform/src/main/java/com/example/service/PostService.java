package com.example.service;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setPublished(updatedPost.isPublished());
            postRepository.save(post);
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

