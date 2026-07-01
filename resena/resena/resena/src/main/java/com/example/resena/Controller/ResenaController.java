package com.example.resena.Controller;

import com.example.resena.Model.Resena;
import com.example.resena.Service.ResenaService;
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
@RequestMapping("/resenas")
@Tag(name = "Controlador de Reseñas", description = "Endpoints para la gestión, publicación y consulta de opiniones de videojuegos")
public class ResenaController {

    @Autowired
    private ResenaService service;

    // POST: Crear una reseña nueva
    @Operation(
        summary = "Crear una nueva reseña",
        description = "Registra una opinión y calificación para un videojuego en el sistema, validando los campos obligatorios del cuerpo JSON."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña guardada y creada con éxito."),
        @ApiResponse(responseCode = "400", description = "Los datos del cuerpo JSON enviados son inválidos o incompletos."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/crear")
    public ResponseEntity<Resena> crear(@Valid @RequestBody Resena resena) {
        Resena guardada = service.guardarResena(resena);
        return ResponseEntity.status(201).body(guardada);
    }

    // GET: Listar reseñas por juego
    @Operation(
        summary = "Listar reseñas por ID de juego",
        description = "Devuelve una lista con todos los comentarios y calificaciones vinculadas a un videojuego específico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida correctamente."),
        @ApiResponse(responseCode = "404", description = "No se encontraron opiniones para el juego especificado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<Resena>> listarPorJuego(@PathVariable Integer idJuego) {
        List<Resena> resenas = service.obtenerResenasPorJuego(idJuego);
        return ResponseEntity.status(200).body(resenas);
    }
}