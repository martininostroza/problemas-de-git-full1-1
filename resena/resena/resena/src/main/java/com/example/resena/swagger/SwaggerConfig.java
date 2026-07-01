package com.example.resena.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI resenasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reseñas")
                        .description("API REST para la gestión de comentarios, opiniones y calificaciones de los videojuegos")
                        .version("1.0")
                );
    }
}