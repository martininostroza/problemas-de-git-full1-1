package com.example.soporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; // <-- Agregamos este import para Swagger

@Entity
@Table(name = "tickets_soporte")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", nullable = false, updatable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // <-- Esto saca el idTicket del body de Swagger
    private Integer idTicket;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo válido")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotBlank(message = "El asunto del ticket es obligatorio")
    @Size(max = 150, message = "El asunto no puede superar los 150 caracteres")
    @Column(name = "asunto", nullable = false, length = 150)
    private String asunto;

    @NotBlank(message = "La descripción del problema es obligatoria")
    @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
    @Column(name = "descripcion", nullable = false, length = 2000)
    private String descripcion;

    @NotBlank(message = "El estado del ticket es obligatorio")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    @Column(name = "estado", nullable = false, length = 30)
    private String estado;
}