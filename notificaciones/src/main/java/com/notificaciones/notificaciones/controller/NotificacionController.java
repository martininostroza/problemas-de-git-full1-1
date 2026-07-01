package com.notificaciones.notificaciones.controller;

import com.notificaciones.notificaciones.model.Notificacion;
import com.notificaciones.notificaciones.service.NotificacionService;
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
@RequestMapping("/notificaciones")
@Tag(name = "Controlador de Notificaciones", description = "Endpoints para la creación, consulta, actualización de estado y eliminación de alertas del sistema")
public class NotificacionController {
    
    @Autowired
    private NotificacionService service;

    // POST: Enviar / Crear notificación
    @Operation(
        summary = "Crear o enviar una notificación",
        description = "Registra una nueva alerta o mensaje informativo en el sistema, comúnmente sincronizado con llamadas de otros módulos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación creada y enviada con éxito."),
        @ApiResponse(responseCode = "400", description = "El cuerpo JSON tiene datos inválidos o faltantes."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/enviar")
    public ResponseEntity<Notificacion> crearNotificacion(@Valid @RequestBody Notificacion notificacion){
        Notificacion nuevaNotificacion = service.save(notificacion);
        return ResponseEntity.status(201).body(nuevaNotificacion);
    }

    // GET: Obtener notificaciones de un usuario
    @Operation(
        summary = "Obtener notificaciones de un usuario por ID",
        description = "Devuelve la lista de alertas vinculadas a un usuario en específico mediante su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones cargada correctamente."),
        @ApiResponse(responseCode = "404", description = "El usuario no tiene notificaciones registradas actualmente."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/obtener-notificaciones/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesDeUsuario(@PathVariable Integer usuarioId) {
        List<Notificacion> notificaciones = service.obtenerNotificacionesDeUsuario(usuarioId);
        
        if (notificaciones.isEmpty()) {
            return ResponseEntity.status(404).body(notificaciones); 
        }
        
        return ResponseEntity.status(200).body(notificaciones);
    }

    // PUT: Marcar como leída
    @Operation(
        summary = "Marcar notificación como leída",
        description = "Cambia el estado de lectura de una notificación específica utilizando su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación actualizada a estado 'leída' con éxito."),
        @ApiResponse(responseCode = "404", description = "La notificación con el ID proporcionado no existe."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PutMapping("/{id}/leido")
    public ResponseEntity<String> marcarComoLeida(@PathVariable Integer id) {
        service.marcarComoLeida(id);
        return ResponseEntity.status(200).body("Notificación marcada como leída exitosamente.");
    }

    // DELETE: Eliminar notificación
    @Operation(
        summary = "Eliminar una notificación por ID",
        description = "Remueve permanentemente del sistema una notificación utilizando su ID único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación eliminada de la base de datos de manera exitosa."),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna notificación con el ID provisto."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @DeleteMapping("/eliminar-notificacion/{id}")
    public ResponseEntity<String> eliminarNotificacion(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(200).body("Notificación eliminada exitosamente.");
    }
}