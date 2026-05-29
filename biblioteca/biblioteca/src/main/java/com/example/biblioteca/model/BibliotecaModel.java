package com.example.biblioteca.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;  

@Data
@AllArgsConstructor 
@NoArgsConstructor  
@Entity
@Table(name = "biblioteca_juegos_usuarios")
public class BibliotecaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El id del juego es obligatorio")
    private String titulo; 
}