package com.carrito.carrito.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class GameData {

    @NotNull(message = "El ID del juego no puede ser nulo")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo")
    private Integer idGame;

    @NotBlank(message = "El título del juego no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La descripción del juego no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio del juego no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio del juego no puede ser negativo")
    private BigDecimal precio;

    @NotBlank(message = "La categoría del juego no puede estar vacía")
    private String categoria;
}