package com.example.resena.Service;

import com.example.resena.Model.Resena;
import com.example.resena.Repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyección del Bean administrado por Spring (Debe tener @LoadBalanced)

    public Resena guardarResena(Resena resena) {
        // CAMBIO: Se reemplaza 'localhost:8081' por el nombre del microservicio en Eureka 'catalogo'
        String url = "http://catalogo/catalogo/buscar-id/" + resena.getIdJuego();

        // COMUNICACIÓN VIA EUREKA: Validar si el juego existe en el catálogo remoto antes de guardar la reseña
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            // CAMBIO: Si falla la comunicación o el juego no existe, devolvemos un 400 Bad Request claro
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "No se puede publicar la reseña: El videojuego con ID " + resena.getIdJuego() + " no existe en el catálogo o el servicio está caído."
            );
        }

        // Regla de negocio: Validar que el usuario no haya escrito ya una reseña para este videojuego
        List<Resena> resenasDelJuego = repository.findByIdJuego(resena.getIdJuego());
        boolean yaResenado = resenasDelJuego.stream()
                .anyMatch(r -> r.getIdUsuario().equals(resena.getIdUsuario()));

        if (yaResenado) {
            // CAMBIO: Devolvemos HttpStatus.BAD_REQUEST en lugar de gatillar un error 500
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Ya has publicado una reseña para este videojuego. Solo se permite una opinión por usuario."
            );
        }

        return repository.save(resena);
    }

    public List<Resena> obtenerResenasPorJuego(Integer idJuego) {
        List<Resena> resenas = repository.findByIdJuego(idJuego);
        if (resenas.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No se encontraron reseñas para el videojuego con ID: " + idJuego
            );
        }
        return resenas;
    }
}