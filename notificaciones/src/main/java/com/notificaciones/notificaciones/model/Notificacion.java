package com.notificaciones.notificaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificaciones")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private Integer idNotificacion;

    @JsonProperty("usuarioId") 
    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser un número entero positivo válido")
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @NotBlank(message = "El mensaje de la notificación es obligatorio")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private Boolean leido = false; 
}