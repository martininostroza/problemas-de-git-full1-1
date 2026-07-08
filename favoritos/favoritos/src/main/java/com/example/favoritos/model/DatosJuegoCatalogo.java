package com.example.favoritos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) 
public class DatosJuegoCatalogo {
    
    @JsonProperty("idGame")
    @NotNull(message = "El ID del juego no puede ser nulo")
    @Min(value = 1, message = "El ID del juego debe ser un número entero positivo")
    private Integer idGame; 

    @NotBlank(message = "El título del juego no puede estar vacío")
    private String titulo;
}