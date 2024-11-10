package com.example.service;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
//                .password(user.getPassword())
//                .authorities("USER")
//                .build();
//    }

    public User loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }
}

