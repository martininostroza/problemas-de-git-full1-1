package com.example.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.biblioteca.model.BibliotecaModel;
import com.example.biblioteca.service.BibliotecaService;
import java.util.List;

// El CONTROLADOR expone los endpoints en el puerto 8087.
// Solo permite registrar (POST) y listar (GET). No permite modificaciones (PUT).
@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    // Endpoint que llamará el Carrito para asignar un juego
    @PostMapping("/agregar")
    public ResponseEntity<BibliotecaModel> agregarJuego(@Valid @RequestBody BibliotecaModel registro) {
        BibliotecaModel nuevoRegistro = service.agregarALaBiblioteca(registro);
        
        // NOTA: Aquí es donde idealmente se llamaría al microservicio de Notificaciones
        // ejemplo: feignClient.enviarNotificacion(registro.getUsuarioId(), "¡Nuevo juego añadido!");
        
        return ResponseEntity.status(201).body(nuevoRegistro);
    }

    // Endpoint para consultar los juegos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<BibliotecaModel>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<BibliotecaModel> lista = service.obtenerBibliotecaDelUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}