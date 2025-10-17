package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ScreenshotServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenshotServerApplication.class, args);
	}

	//инициализация пользователя
	@Bean
	public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("client").isEmpty()) {
				User clientUser = new User();
				clientUser.setUsername("client");
				clientUser.setPassword(passwordEncoder.encode("password123"));
				clientUser.setRole("USER");

				userRepository.save(clientUser);
			}
		};
	}
}
