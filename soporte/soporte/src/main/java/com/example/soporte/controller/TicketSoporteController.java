package com.example.soporte.controller;

import com.example.soporte.model.TicketSoporte;
import com.example.soporte.service.TicketSoporteService;
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
@RequestMapping("/soporte")
@Tag(name = "Controlador de Soporte", description = "Endpoints para la creación de tickets de soporte y consulta de asistencia técnica por usuario")
public class TicketSoporteController {

    @Autowired
    private TicketSoporteService service;

    // POST: Crear un nuevo ticket de soporte
    @Operation(
        summary = "Crear un nuevo ticket de soporte",
        description = "Registra una solicitud de asistencia técnica en la base de datos, delegando la validación del usuario al Service."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket de soporte generado con éxito."),
        @ApiResponse(responseCode = "400", description = "Los datos del cuerpo JSON provistos son inválidos o faltantes."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/crear")
    public ResponseEntity<TicketSoporte> crearTicket(@Valid @RequestBody TicketSoporte ticket) {
        TicketSoporte guardado = service.crearTicket(ticket);
        return ResponseEntity.status(201).body(guardado);
    }

    // GET: Listar tickets de soporte por usuario
    @Operation(
        summary = "Listar tickets por ID de usuario",
        description = "Devuelve el historial completo de tickets de soporte técnico que han sido abiertos por un usuario específico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tickets cargada con éxito."),
        @ApiResponse(responseCode = "404", description = "No se encontraron solicitudes de soporte para el usuario especificado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<TicketSoporte>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<TicketSoporte> tickets = service.obtenerTicketsPorUsuario(idUsuario);
        return ResponseEntity.status(200).body(tickets);
    }
}