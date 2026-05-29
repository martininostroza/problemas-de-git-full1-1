package com.carrito.carrito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "item_carrito")
@Data
public class ItemCarrito {

    @Id
    @Column(name = "id_item", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;
    
    @NotNull(message = "El ID del juego es obligatorio")
    @Column(name = "id_game", nullable = false)
    private Integer idGame;
    
    @NotBlank(message = "El título es necesario")
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Min(value = 1, message = "Mínimo 1 unidad")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @NotNull(message = "El precio es obligatorio")
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

}