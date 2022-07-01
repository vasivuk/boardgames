package com.vasivuk.boardgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BoardgamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardgamesApplication.class, args);
	}

}
