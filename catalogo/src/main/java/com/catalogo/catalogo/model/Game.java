package com.catalogo.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "catalogo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game", nullable = false, updatable = false)
    private Integer idGame;

    @NotBlank(message = "El título del juego es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "La descripción del juego es obligatoria")
    @Size(max = 2048, message = "La descripción no puede superar los 2048 caracteres")
    @Column(name = "descripcion", nullable = false, length = 2048)
    private String descripcion;

    @NotNull(message = "El precio del juego es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio del juego no puede ser un valor negativo")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @NotBlank(message = "La categoría del juego es obligatoria")
    @Size(max = 50, message = "La categoría no puede superar los 50 caracteres")
    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;
}