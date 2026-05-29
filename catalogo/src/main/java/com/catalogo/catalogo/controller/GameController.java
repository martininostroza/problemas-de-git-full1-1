package com.catalogo.catalogo.controller;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalogo")
public class GameController {

    @Autowired
    private GameService service;

    @GetMapping("/listar")
    public List<Game> listGames() {
        return service.findAll();
    }

    @GetMapping("/buscar-id/{id}")
    public Optional<Game> getGameById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game game) {
        Game newGame = service.agregar(game);
        return ResponseEntity.status(201).body(newGame);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> updateGame(@PathVariable Integer id, @Valid @RequestBody Game gameUpdate) {
        Optional<Game> gameExisting = service.findById(id);
        if(gameExisting.isPresent()) {
            return ResponseEntity.status(200).body("El juego con ID " + id + " ha sido actualizado");
        } else {
            return ResponseEntity.status(404).body("No se encontró el juego para actualizar");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Integer id) {
        Optional<Game> gameExisting = service.findById(id);
        if(gameExisting.isPresent()) {
            service.delete(id);
            return ResponseEntity.status(200).body("El juego con ID " + id + " ha sido eliminado");
        } else {
            return ResponseEntity.status(404).body("No se encontró el juego para eliminar");
        }
    }

}
