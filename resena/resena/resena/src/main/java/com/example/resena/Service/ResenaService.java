package com.example.resena.Service;

import com.example.resena.Model.Resena;
import com.example.resena.Repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyección correcta del Bean administrado por Spring

    public Resena guardarResena(Resena resena) {
        String url = "http://localhost:8081/catalogo/buscar-id/" + resena.getIdJuego();

        // COMUNICACIÓN / EXCEPCIÓN: Validar si el juego existe en el catálogo remoto antes de evaluar la reseña
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se puede publicar la reseña: El videojuego con ID " + resena.getIdJuego() + " no existe en el catálogo.");
        }

        // la regla de negocio es Validar que el usuario no haya escrito ya una reseña para este videojuego
        List<Resena> resenasDelJuego = repository.findByIdJuego(resena.getIdJuego());
        boolean yaResenado = resenasDelJuego.stream()
                .anyMatch(r -> r.getIdUsuario().equals(resena.getIdUsuario()));

        if (yaResenado) {
            throw new IllegalArgumentException("Ya has publicado una reseña para este videojuego. Solo se permite una opinión por usuario.");
        }

        return repository.save(resena);
    }

    public List<Resena> obtenerResenasPorJuego(Integer idJuego) {
        List<Resena> resenas = repository.findByIdJuego(idJuego);
        if (resenas.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron reseñas para el videojuego con ID: " + idJuego);
        }
        return resenas;
    }
}