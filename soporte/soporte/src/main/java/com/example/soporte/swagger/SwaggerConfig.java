package com.example.soporte.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI soporteOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Soporte Técnico")
                        .description("API REST para la gestión de tickets de asistencia, reportes de problemas y atención al usuario")
                        .version("1.0")

                );
    }
}