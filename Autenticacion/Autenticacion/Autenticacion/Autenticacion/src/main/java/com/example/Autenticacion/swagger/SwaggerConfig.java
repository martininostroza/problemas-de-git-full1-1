package com.example.Autenticacion.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI autenticacionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autenticación")
                        .description("API REST para la gestión de usuarios y seguridad")
                        .version("1.0")
                );
    }
}