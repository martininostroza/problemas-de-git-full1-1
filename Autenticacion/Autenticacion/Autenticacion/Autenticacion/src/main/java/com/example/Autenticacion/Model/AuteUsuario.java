package com.example.Autenticacion.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Aute_Usuarios")
public class AuteUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede superar los 100 caracteres")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "El rol del usuario es obligatorio")
    @Size(max = 20, message = "El rol no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    private String rol; // Ejemplo: "CLIENTE", "ADMIN"
}