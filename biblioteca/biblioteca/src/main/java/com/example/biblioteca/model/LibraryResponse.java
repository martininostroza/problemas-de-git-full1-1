package com.biblioteca.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse {
    private Integer idLibraryEntry;
    private Integer idUsuario;
    private Integer idGame;
    private String estado;
}