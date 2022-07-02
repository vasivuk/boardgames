package com.vasivuk.boardgames;

import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardgamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardgamesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CategoryRepository repository) throws Exception {
		return (String[] args ) -> {
			Category c1 = new Category("category 1");
			Category c2 = new Category("category 2");
			repository.save(c1);
			repository.save(c2);
		};
	}
}
