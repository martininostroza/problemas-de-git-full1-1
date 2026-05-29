package com.example.biblioteca.controller;

import com.example.biblioteca.model.EntradaBiblioteca;
import com.example.biblioteca.model.RespuestaBiblioteca;
import com.example.biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    // Procesa la compra del carrito de un usuario, migrando los juegos a su biblioteca y disparando la notificación
    @PostMapping("/comprar/{idUsuario}")
    public List<RespuestaBiblioteca> comprarCarrito(@PathVariable Integer idUsuario) {
        return service.comprarCarrito(idUsuario);
    }

    // Expone la lista de registros de juegos permanentes adquiridos por un usuario específico
    @GetMapping("/usuario/{idUsuario}")
    public List<EntradaBiblioteca> listarPorUsuario(@PathVariable Integer idUsuario) {
        return service.obtenerBibliotecaPorUsuario(idUsuario);
    }
}