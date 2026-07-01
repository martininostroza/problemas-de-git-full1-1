package com.catalogo.catalogo.controller;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// Importaciones estrictas de Swagger OpenAPI según el PPT del profesor
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@Tag(name = "Controlador de Catálogo", description = "Endpoints para la gestión, consulta y edición de videojuegos en el catálogo")
public class GameController {

    @Autowired
    private GameService service;

    // GET: Listar todos los juegos
    @Operation(
        summary = "Listar todos los juegos",
        description = "Devuelve una lista con todos los videojuegos guardados actualmente en el catálogo."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de juegos obtenida correctamente."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/listar")
    public List<Game> listGames() {
        return service.findAll();
    }

    // GET: Buscar juego por ID
    @Operation(
        summary = "Buscar un juego por ID",
        description = "Busca un videojuego específico utilizando su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juego encontrado con éxito."),
        @ApiResponse(responseCode = "404", description = "El juego con el ID especificado no existe en el catálogo."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Integer id) {
        Game game = service.findById(id);
        return ResponseEntity.status(200).body(game);
    }

    // POST: Agregar un juego nuevo
    @Operation(
        summary = "Agregar un nuevo juego",
        description = "Registra un nuevo videojuego en el catálogo validando que el cuerpo JSON contenga los datos requeridos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Juego agregado al catálogo con éxito."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes en el cuerpo de la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/agregar")
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game game) {
        Game newGame = service.agregar(game);
        return ResponseEntity.status(201).body(newGame);
    }

    // PUT: Actualizar los datos de un juego
    @Operation(
        summary = "Actualizar un juego por ID",
        description = "Modifica las propiedades de un videojuego existente en el catálogo identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juego actualizado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos."),
        @ApiResponse(responseCode = "404", description = "El juego a actualizar no fue encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer id, @Valid @RequestBody Game gameUpdate) {
        Game updatedGame = service.update(id, gameUpdate);
        return ResponseEntity.status(200).body(updatedGame);
    }

    // DELETE: Borrar un juego del catálogo
    @Operation(
        summary = "Eliminar un juego por ID",
        description = "Remueve permanentemente un videojuego del catálogo utilizando su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El juego ha sido eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "El juego con el ID especificado no existe."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(200).body("El juego con ID " + id + " ha sido eliminado exitosamente.");
    }
}