package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "biblioteca")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LibraryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_library_entry", nullable = false, updatable = false)
    private Integer idLibraryEntry;

    @NotNull(message = "El ID del usuario no puede estar vacío")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "El ID del juego no puede estar vacío")
    @Column(name = "id_game", nullable = false)
    private Integer idGame;
}
