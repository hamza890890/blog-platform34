package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.repository")
public class BlogPlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogPlatformApplication.class, args);
	}
}
