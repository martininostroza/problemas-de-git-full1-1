package com.example.biblioteca.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bibliotecaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Biblioteca")
                        .description("API REST para la gestión del catálogo de libros y préstamos")
                        .version("1.0")

                );
    }
}