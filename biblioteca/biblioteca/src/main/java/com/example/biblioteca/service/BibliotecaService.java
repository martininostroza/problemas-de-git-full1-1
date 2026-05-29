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

    // Recupera por HTTP los artículos del carrito de un usuario, los almacena permanentemente en la biblioteca y envía una alerta al servicio de notificaciones
    public List<RespuestaBiblioteca> comprarCarrito(Integer idUsuario) {
        
        RestTemplate restTemplate = new RestTemplate();
        String urlCarrito = "http://localhost:8081/carrito/" + idUsuario + "/juegos";
        
        DatosArticuloCarrito[] itemsCarrito = restTemplate.getForObject(urlCarrito, DatosArticuloCarrito[].class);
        List<RespuestaBiblioteca> respuestas = new ArrayList<>();
        
        for (DatosArticuloCarrito item : itemsCarrito) {
            EntradaBiblioteca entrada = new EntradaBiblioteca();
            entrada.setIdUsuario(idUsuario);
            entrada.setIdJuego(item.getIdJuego());
            
            repository.save(entrada);
            
            RespuestaBiblioteca respuesta = new RespuestaBiblioteca();
            respuesta.setIdEntradaBiblioteca(entrada.getIdEntradaBiblioteca());
            respuesta.setIdUsuario(entrada.getIdUsuario());
            respuesta.setIdJuego(entrada.getIdJuego());
            respuesta.setEstado("Guardado con éxito");
            
            respuestas.add(respuesta);
        }
        
        String urlNotificaciones = "http://localhost:8082/notificaciones/enviar";
        PeticionNotificacion notificacion = new PeticionNotificacion();
        notificacion.setIdUsuario(idUsuario);
        notificacion.setMensaje("¡Tus juegos han sido guardados con éxito en tu biblioteca!");
        
        restTemplate.postForObject(urlNotificaciones, notificacion, String.class);
        
        return respuestas;
    }

    // Devuelve todos los registros de juegos adquiridos pertenecientes a un usuario específico
    public List<EntradaBiblioteca> obtenerBibliotecaPorUsuario(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }
}