package com.vasivuk.boardgames.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("In cors mappings method");
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.ACCEPT, HttpHeaders.SET_COOKIE)
//                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
//                .allowCredentials(true);

        registry.addMapping("/api/login").allowedOrigins("http://localhost:3000")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);

        registry.addMapping("/api/token/refresh").allowedOrigins("http://localhost:3000")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);
        registry.addMapping(("/api/**")).allowedOrigins(("http://localhost:3000"));
    }
}