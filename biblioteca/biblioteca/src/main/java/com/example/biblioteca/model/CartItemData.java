package com.biblioteca.biblioteca.model;

import lombok.Data;

@Data
public class CartItemData {
    private Integer idCartItem;
    private Integer idUsuario;
    private Integer idGame;
    private Integer cantidad;
}