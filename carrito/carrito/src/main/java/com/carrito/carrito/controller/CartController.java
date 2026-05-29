package com.carrito.carrito.controller;

import com.carrito.carrito.model.CartItem;
import com.carrito.carrito.model.CartResponse;
import com.carrito.carrito.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CartController {

    @Autowired
    private CartService service;
    //http://localhost:8086/carrito/agregar
    //{
    //   "idUsuario": 1,
    //   "idJuego": 2,
    //   "cantidad": 1
    //}

 

    // Retorna la lista completa de artículos en el carrito cruzados con la información de catálogo
    @GetMapping("/{idUsuario}/juegos-carrito")
    public List<CartResponse> obtenerCarrito(@PathVariable Integer idUsuario) {
        return service.obtenerCarrito(idUsuario);
    }

    // Registra un nuevo elemento en la base de datos del carrito devolviendo estado HTTP 201
    @PostMapping("/agregar")
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody CartItem item) {
        CartItem newItem = service.agregar(item);
        return ResponseEntity.status(201).body(newItem);
    }

    // Procesa la eliminación física de un elemento según su ID y responde basándose en el éxito de la operación
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer id) {
        boolean eliminado = service.delete(id);
        
        if (eliminado) {
            return ResponseEntity.status(200).body("El artículo con ID " + id + " ha sido eliminado");
        } 
        
        return ResponseEntity.status(404).body("No se encontró el artículo para eliminar");
    }
}   
