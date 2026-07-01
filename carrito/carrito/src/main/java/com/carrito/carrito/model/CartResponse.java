package com.carrito.carrito.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    @NotNull(message = "El ID del artículo del carrito no puede ser nulo")
    @Min(value = 1, message = "El ID del artículo debe ser un número entero positivo")
    private Integer idCartItem;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo")
    private Integer idUsuario;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private Integer cantidad;

    @NotNull(message = "Los datos del juego son obligatorios")
    @Valid 
    private GameData juego; 
}