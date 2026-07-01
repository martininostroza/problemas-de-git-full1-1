package com.example.biblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/biblioteca")
@Tag(name = "Controlador de Biblioteca", description = "Endpoints para la gestión de usuarios y consulta en el módulo de biblioteca")
public class BibliotecaController {

    @Operation(
        summary = "Obtener datos de un usuario por ID",
        description = "Consulta la base de datos de biblioteca mediante el ID del usuario y retorna su información detallada."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario encontrado con éxito. Retorna los datos correspondientes.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
        ),
        @ApiResponse(responseCode = "404", description = "El identificador de usuario no existe en la base de datos de biblioteca."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerUsuarioPorId(
        @Parameter(description = "Identificador único del usuario a consultar", example = "1")
        @PathVariable Integer idUsuario
    ) {
     
        Map<String, Object> respuestaSimulada = new HashMap<>();
        respuestaSimulada.put("idUsuario", idUsuario);
        respuestaSimulada.put("nombre", "Usuario Ejemplo");
        respuestaSimulada.put("librosPrestados", 3);
        
        return ResponseEntity.ok(respuestaSimulada);
    }
    @Operation(
        summary = "Crear o registrar en biblioteca",
        description = "Recibe un cuerpo JSON con la información necesaria para almacenar un registro nuevo."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Registro creado con éxito en el módulo de biblioteca.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
        ),
        @ApiResponse(responseCode = "400", description = "El cuerpo de la petición (JSON) tiene campos inválidos o vacíos."),
        @ApiResponse(responseCode = "500", description = "Error interno al intentar guardar en el servidor.")
    })
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarElemento(
        @RequestBody(
            description = "Datos necesarios para la creación",
            required = true,
            content = @Content(
                mediaType = "application/json",
                // Esto simula el esquema del JSON que debes rellenar en la interfaz de Swagger
                schema = @Schema(example = "{\"idUsuario\": 1, \"nombre\": \"Juan Perez\", \"accion\": \"registro\"}")
            )
        )
        @org.springframework.web.bind.annotation.RequestBody Map<String, Object> datos
    ) {
        // Tu lógica para guardar (puedes reemplazar esto con tu código real)
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Registro guardado exitosamente en biblioteca");
        res.put("datosRecibidos", datos);

        return ResponseEntity.status(201).body(res);
    }
}