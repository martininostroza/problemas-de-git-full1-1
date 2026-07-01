package com.carrito.carrito.controller;

import com.carrito.carrito.model.CartItem;
import com.carrito.carrito.model.CartResponse;
import com.carrito.carrito.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/carrito")
@Tag(name = "Controlador de Carrito", description = "Endpoints para gestionar los artículos añadidos al carrito de compras")
public class CartController {

    @Autowired
    private CartService service;

    // GET: Recupera los juegos en el carrito
    @Operation(
        summary = "Obtener artículos del carrito por usuario",
        description = "Recupera la lista de juegos que un usuario específico tiene guardados en su carrito de compras."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente."),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos en el carrito para el usuario proporcionado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/{idUsuario}/juegos")
    public ResponseEntity<?> obtenerCarrito(@PathVariable Integer idUsuario) {
        List<CartResponse> carrito = service.obtenerCarrito(idUsuario);
        
        if (carrito.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron productos en el carrito del usuario: " + idUsuario);
        }
        
        return ResponseEntity.status(200).body(carrito);
    }

    // POST: Agrega un juego nuevo al carrito
    @Operation(
        summary = "Agregar un juego al carrito",
        description = "Añade un nuevo artículo al carrito de compras o incrementa su cantidad si ya existe, validando los datos de entrada."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Artículo agregado o actualizado en el carrito con éxito."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes en el cuerpo de la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/agregar")
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody CartItem item) {
        CartItem newItem = service.agregar(item);
        return ResponseEntity.status(201).body(newItem);
    }

    // DELETE: Borra un juego del carrito
    @Operation(
        summary = "Eliminar un artículo del carrito por ID",
        description = "Remueve permanentemente un artículo del carrito utilizando su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El artículo ha sido eliminado exitosamente del carrito."),
        @ApiResponse(responseCode = "404", description = "El artículo con el ID especificado no se encontró en el carrito."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer id) {
        service.delete(id); 
        return ResponseEntity.status(200).body("El artículo con ID " + id + " ha sido eliminado exitosamente del carrito.");
    }
}