package com.example.resena.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resenas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena", nullable = false, updatable = false)
    private Integer idResena;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo válido")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "El ID del juego es obligatorio")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo válido")
    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @NotBlank(message = "El comentario de la reseña es obligatorio")
    @Size(max = 1000, message = "El comentario no puede superar los 1000 caracteres")
    @Column(name = "comentario", nullable = false, length = 1000)
    private String comentario;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima permitida es 1 estrella")
    @Max(value = 5, message = "La calificación máxima permitida es 5 estrellas")
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;
}
