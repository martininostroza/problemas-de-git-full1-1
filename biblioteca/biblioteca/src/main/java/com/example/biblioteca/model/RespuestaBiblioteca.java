package com.example.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaBiblioteca {
    private Integer idEntradaBiblioteca;
    private Integer idUsuario;
    private Integer idJuego;
    private String estado;
}