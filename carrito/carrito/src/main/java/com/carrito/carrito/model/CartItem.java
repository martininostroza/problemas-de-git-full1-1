package com.carrito.carrito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart_item", nullable = false, updatable = false)
    private Integer idCartItem;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo válido")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "El ID del juego es obligatorio")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo válido")
    @Column(name = "id_game", nullable = false)
    private Integer idGame;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima permitida es 1")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}