package com.notificaciones.notificaciones.config;

import com.notificaciones.notificaciones.model.Notificacion;
import com.notificaciones.notificaciones.service.NotificacionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(NotificacionService service) {
        return args -> {
            if (service.count() == 0) {

                // Se eliminó LocalDateTime.now() del constructor
                service.save(new Notificacion(null, 1, "Se han agregado 3 videojuegos nuevos", false));

                crearNotificacionSimple(service, 1, "Tu pedido #98765 acaba de ser confirmado.");
                
                System.out.println("--- Notificaciones iniciales cargadas correctamente ---");
            }
        };
    }

    private void crearNotificacionSimple(NotificacionService service, Integer usuarioId, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioId(usuarioId);
        notificacion.setMensaje(mensaje);
        notificacion.setLeido(false);
        // Se eliminó la línea de setFechaCreacion
        service.save(notificacion);
    }
}