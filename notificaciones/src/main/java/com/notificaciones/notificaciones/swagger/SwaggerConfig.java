package com.notificaciones.notificaciones.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificacionesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Notificaciones")
                        .description("API REST para el envío de alertas, correos informativos y notificaciones del sistema")
                        .version("1.0")
                );
    }
}