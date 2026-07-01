package com.example.resena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ResenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResenaApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
