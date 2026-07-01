package com.example.biblioteca.service;

import com.example.biblioteca.model.DatosArticuloCarrito;
import com.example.biblioteca.model.EntradaBiblioteca;
import com.example.biblioteca.model.RespuestaBiblioteca;
import com.example.biblioteca.model.PeticionNotificacion;
import com.example.biblioteca.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyección correcta del Bean configurado

    // Recupera por HTTP los artículos del carrito de un usuario, los almacena permanentemente en la biblioteca y envía una alerta al servicio de notificaciones
    public List<RespuestaBiblioteca> comprarCarrito(Integer idUsuario) {
        
        String urlCarrito = "http://localhost:8086/carrito/" + idUsuario + "/juegos";
        DatosArticuloCarrito[] itemsCarrito = restTemplate.getForObject(urlCarrito, DatosArticuloCarrito[].class);
        
        // la regla de negocio Validar si el carrito remoto está vacío o no responde
        if (itemsCarrito == null || itemsCarrito.length == 0) {
            throw new IllegalArgumentException("No se encontraron artículos en el carrito para procesar la compra");
        }
        
        List<RespuestaBiblioteca> respuestas = new ArrayList<>();
        
        for (DatosArticuloCarrito item : itemsCarrito) {
            // la regla de negocio es Verificar si el usuario ya posee este juego en su biblioteca
            boolean yaExiste = repository.findByIdUsuario(idUsuario).stream()
                    .anyMatch(entrada -> entrada.getIdJuego().equals(item.getIdJuego()));
            
            RespuestaBiblioteca respuesta = new RespuestaBiblioteca();
            respuesta.setIdUsuario(idUsuario);
            respuesta.setIdJuego(item.getIdJuego());
            
            if (yaExiste) {
                respuesta.setEstado("Omitido: El juego ya se encuentra en la biblioteca");
            } else {
                EntradaBiblioteca entrada = new EntradaBiblioteca();
                entrada.setIdUsuario(idUsuario);
                entrada.setIdJuego(item.getIdJuego());
                
                repository.save(entrada);
                
                respuesta.setIdEntradaBiblioteca(entrada.getIdEntradaBiblioteca());
                respuesta.setEstado("Guardado con éxito");
            }
            
            respuestas.add(respuesta);
        }
        
        // COMUNICACIÓN: Enviar la alerta al servicio de notificaciones
        String urlNotificaciones = "http://localhost:8082/notificaciones/enviar";
        PeticionNotificacion notificacion = new PeticionNotificacion();
        notificacion.setIdUsuario(idUsuario);
        notificacion.setMensaje("¡Tus juegos han sido procesados con éxito en tu biblioteca!");
        
        try {
            restTemplate.postForObject(urlNotificaciones, notificacion, String.class);
        } catch (Exception e) {
            // Loguear o manejar si el servicio de notificaciones está caído sin tumbar la transacción principal
        }
        
        return respuestas;
    }

    // Devuelve todos los registros de juegos adquiridos pertenecientes a un usuario específico
    public List<EntradaBiblioteca> obtenerBibliotecaPorUsuario(Integer idUsuario) {
        List<EntradaBiblioteca> biblioteca = repository.findByIdUsuario(idUsuario);
        if (biblioteca.isEmpty()) {
            throw new IllegalArgumentException("No se encontró ninguna biblioteca o juegos registrados para el usuario con ID: " + idUsuario);
        }
        return biblioteca;
    }
}