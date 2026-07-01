package com.example.biblioteca.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DatosArticuloCarrito {

    @NotNull(message = "El ID del artículo del carrito es obligatorio")
    @Min(value = 1, message = "El ID del artículo debe ser un número entero positivo")
    private Integer idArticuloCarrito;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo")
    private Integer idUsuario;

    @NotNull(message = "El ID del juego es obligatorio")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo")
    private Integer idJuego;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima de artículos debe ser 1")
    private Integer cantidad;
}