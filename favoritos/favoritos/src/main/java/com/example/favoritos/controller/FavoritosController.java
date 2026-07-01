package com.example.favoritos.controller;

import com.example.favoritos.model.JuegoFavorito;
import com.example.favoritos.service.FavoritosService;
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
@RequestMapping("/favoritos")
@Tag(name = "Controlador de Favoritos", description = "Endpoints para gestionar la lista de videojuegos favoritos de los usuarios")
public class FavoritosController {

    @Autowired
    private FavoritosService service;

    // POST: Agrega un juego a favoritos
    @Operation(
        summary = "Agregar un juego a favoritos",
        description = "Registra un nuevo videojuego dentro de la lista de favoritos de un usuario, validando la entrada de datos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Juego agregado a favoritos con éxito."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes en el cuerpo de la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/agregar")
    public ResponseEntity<JuegoFavorito> agregar(@Valid @RequestBody JuegoFavorito favorito) {
        JuegoFavorito guardado = service.agregarAFavoritos(favorito);
        return ResponseEntity.status(201).body(guardado);
    }

    // GET: Muestra todos los juegos favoritos de un usuario
    @Operation(
        summary = "Listar favoritos por usuario",
        description = "Devuelve la lista completa de videojuegos marcados como favoritos por un identificador único de usuario."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de favoritos obtenida correctamente."),
        @ApiResponse(responseCode = "404", description = "No se encontraron favoritos para el usuario especificado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<JuegoFavorito>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<JuegoFavorito> favoritos = service.obtenerFavoritosPorUsuario(idUsuario);
        return ResponseEntity.status(200).body(favoritos);
    }
}