package com.example.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.biblioteca.model.BibliotecaModel;
import com.example.biblioteca.service.BibliotecaService;
import java.util.List;

//http://localhost:8087/biblioteca
// {
    //     "usuarioId": 1,
    //     "titulo": "Minecraft"
    // }


@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    // Endpoint que llamará el Carrito para asignar un juego
    @PostMapping("/agregar")
    public ResponseEntity<BibliotecaModel> agregarJuego(@Valid @RequestBody BibliotecaModel registro) {
        BibliotecaModel nuevoRegistro = service.agregarALaBiblioteca(registro);
        return ResponseEntity.status(201).body(nuevoRegistro);
    }


    // Endpoint para ver el contenido global de la tabla
      @GetMapping("/todo")
      public ResponseEntity<List<BibliotecaModel>> listarTodo() {
      List<BibliotecaModel> lista = service.obtenerTodoGlobal();
      return ResponseEntity.ok(lista);
      }


    // Endpoint para consultar los juegos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<BibliotecaModel>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<BibliotecaModel> lista = service.obtenerBibliotecaDelUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}