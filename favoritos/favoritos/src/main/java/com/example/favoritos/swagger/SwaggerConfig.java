package com.example.favoritos.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI favoritosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Favoritos")
                        .description("API REST para gestionar la lista de juegos favoritos de los usuarios")
                        .version("1.0")

                );
    }
}