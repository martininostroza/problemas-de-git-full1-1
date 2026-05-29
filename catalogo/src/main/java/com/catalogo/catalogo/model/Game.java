package com.catalogo.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @NotBlank(message = "El título no puede estar vacío")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "descripcion", nullable = false, length = 2048)
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser un valor positivo")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @NotBlank(message = "La categoría no puede estar vacía")
    @Column(name = "categoria", nullable = false)
    private String categoria;

}
