package com.example.favoritos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favoritos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JuegoFavorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego_favorito", nullable = false, updatable = false)
    private Integer idJuegoFavorito;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo válido")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "El ID del juego es obligatorio")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo válido")
    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;
}