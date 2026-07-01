package com.example.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

    //Con esto Spring Boot ya sabrá cómo inyectar el RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}