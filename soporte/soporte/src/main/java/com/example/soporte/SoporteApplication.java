package com.example.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SoporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoporteApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
