package com.l1rn.user_service;

import com.l1rn.user_service.repository.UserRepository;
import com.netflix.discovery.EurekaNamespace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UserServiceApplication {
	UserRepository userRepository;
	@RequestMapping("/")
	String hello(){
		return userRepository.findAll().toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
