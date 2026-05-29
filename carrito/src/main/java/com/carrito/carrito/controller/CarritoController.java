package com.carrito.carrito.controller;

import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.model.ItemCarrito;
import com.carrito.carrito.service.CarritoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @PostMapping("/{idUsuario}/agregar")
    public ResponseEntity<Carrito> agregar(@PathVariable Integer idUsuario, @Valid @RequestBody ItemCarrito item) {
        Carrito actualizado = service.agregarAlCarrito(idUsuario, item);
        return ResponseEntity.status(201).body(actualizado);
    }

    @PostMapping("/{idUsuario}/finalizar-pago")
    public ResponseEntity<Carrito> pagar(@PathVariable Integer idUsuario) {
        return service.pagarCarrito(idUsuario)
                .map(carrito -> ResponseEntity.status(200).body(carrito))
                .orElse(ResponseEntity.notFound().build());
    }

    

}