package com.notificaciones.notificaciones.service;

import com.notificaciones.notificaciones.model.Notificacion;
import com.notificaciones.notificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> findAll() {
        return repository.findAll();
    }

    public Notificacion findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ninguna notificación con el ID: " + id));
    }

    public Notificacion save(Notificacion notificacion) {
        // Asegurar que una notificación nueva inicie como no leída
        notificacion.setLeido(false);
        return repository.save(notificacion);
    }

    public Notificacion update(Integer id, Notificacion notificacionActualizada) {
        Notificacion notificacionExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar: La notificación con ID " + id + " no existe."));

        notificacionExistente.setUsuarioId(notificacionActualizada.getUsuarioId());
        notificacionExistente.setMensaje(notificacionActualizada.getMensaje());
        
        // CAMBIO AQUÍ: .getLeido() en lugar de .isLeido() por el cambio a Boolean objeto
        notificacionExistente.setLeido(notificacionActualizada.getLeido());
        
        return repository.save(notificacionExistente);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: La notificación con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }

    public List<Notificacion> obtenerNotificacionesDeUsuario(Integer usuarioId) {
        return repository.findByUsuarioIdAndLeidoFalseOrderByIdNotificacionDesc(usuarioId);
    }

    public void marcarComoLeida(Integer id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede modificar: La notificación con ID " + id + " no existe."));
        
        notificacion.setLeido(true);
        repository.save(notificacion);
    }

    public Integer count() {
        return (int) repository.count();
    }

    public void crearNotificacion(Notificacion notificacion) {
        repository.save(notificacion);
    }
}
