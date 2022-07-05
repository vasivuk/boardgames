package com.vasivuk.boardgames;

import com.vasivuk.boardgames.configuration.Roles;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.model.UserRole;
import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BoardgamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardgamesApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public CommandLineRunner run(UserService userService) throws Exception {
//		return args -> {
//			userService.saveRole(new UserRole(null, Roles.ROLE_ADMIN));
//			userService.saveRole(new UserRole(null, Roles.ROLE_USER));
//
//			userService.saveUser(new AppUser("John", "Travolta", "john@gmail.com", "1234"));
//			userService.saveUser(new AppUser("Will", "Smith", "will@gmail.com", "1234"));
//			userService.saveUser(new AppUser("Jim", "Carry", "jim@gmail.com", "1234"));
//			userService.saveUser(new AppUser("Arnold",  "Schwarzenegger", "arnold@gmail.com", "1234"));
//
//			userService.assignRoleToUser("john@gmail.com", Roles.ROLE_USER);
//			userService.assignRoleToUser("will@gmail.com", Roles.ROLE_ADMIN);
//			userService.assignRoleToUser("jim@gmail.com", Roles.ROLE_USER);
//			userService.assignRoleToUser("arnold@gmail.com", Roles.ROLE_USER);
//
//		};
//	}
}
