package com.carrito.carrito.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI carritoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Carrito de Compras")
                        .description("API REST para la gestión de productos dentro del carrito")
                        .version("1.0")                      
                );
    }
}