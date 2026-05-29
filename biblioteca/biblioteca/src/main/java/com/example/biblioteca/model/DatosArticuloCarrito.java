package com.example.biblioteca.model;

import lombok.Data;

@Data
public class DatosArticuloCarrito {
    private Integer idArticuloCarrito;
    private Integer idUsuario;
    private Integer idJuego;
    private Integer cantidad;
}