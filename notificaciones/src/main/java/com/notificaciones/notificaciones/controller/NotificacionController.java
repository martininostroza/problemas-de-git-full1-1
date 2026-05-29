package com.notificaciones.notificaciones.controller;

import com.notificaciones.notificaciones.model.Notificacion;
import com.notificaciones.notificaciones.service.NotificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;


//{
  //     "idUsuario": 1,
   //    "mensaje": "Tu orden de compra ha sido procesada con éxito. ¡Disfruta tu juego!",
   //    "tipo": "Correo"
  // }

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    
    @Autowired
    private NotificacionService service;

    @PostMapping("/crear")
    public ResponseEntity<Notificacion> crearNotificacion(@Valid @RequestBody Notificacion notificacion){
        Notificacion nuevaNotificacion = service.save(notificacion);
        return ResponseEntity.status(201).body(nuevaNotificacion);
    }

    @GetMapping("/obtener-notificaciones/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesDeUsuario(@PathVariable Integer usuarioId) {
        List<Notificacion> notificaciones = service.obtenerNotificacionesDeUsuario(usuarioId);
        return ResponseEntity.status(200).body(notificaciones);
    }

    @PutMapping("/{id}/leido")
    public ResponseEntity<String> marcarComoLeida(@PathVariable Integer id, @Valid @RequestBody Notificacion notificacion) {
        Optional<Notificacion> notificacionExistente = service.findById(id);
        if (notificacionExistente.isPresent()) {
            service.marcarComoLeida(id);
            return ResponseEntity.status(200).body("Notificación marcada como leída");
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/eliminar-notificacion/{id}")
    public ResponseEntity<String> eliminarNotificacion(@PathVariable Integer id) {
        Optional<Notificacion> notificacionExistente = service.findById(id);
        if (notificacionExistente.isPresent()) {
            service.delete(id);
            return ResponseEntity.status(200).body("Notificación eliminada");
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
