package com.example.Autenticacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuteDTO {
    private Integer id;
    private String email;
    private String rol;
}