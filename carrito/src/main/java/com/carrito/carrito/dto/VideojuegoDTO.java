package com.carrito.carrito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VideojuegoDTO {
    private Long id;

    @JsonProperty("titulo") // Mapea el campo 'titulo' del JSON a 'nombre'
    private String nombre;

    private String desarrollador;

    @JsonProperty("categoria") // Mapea el campo 'categoria' del JSON a 'genero'
    private String genero;

    private BigDecimal precio;
}