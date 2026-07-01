package com.example.favoritos.service;

import com.example.favoritos.model.DatosJuegoCatalogo;
import com.example.favoritos.model.JuegoFavorito;
import com.example.favoritos.repository.FavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FavoritosService {

    @Autowired
    private FavoritosRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyección correcta del Bean administrado por Spring

    // Consulta por HTTP al catálogo para verificar la existencia del juego y evita duplicados antes de guardar
    public JuegoFavorito agregarAFavoritos(JuegoFavorito favorito) {
        String url = "http://localhost:8081/catalogo/buscar-id/" + favorito.getIdJuego();
        
        // COMUNICACIÓN / EXCEPCIÓN: Si el juego no existe en el catálogo, saltará una excepción atrapada por el Global Handler
        try {
            restTemplate.getForObject(url, DatosJuegoCatalogo.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se puede agregar a favoritos: El videojuego con ID " + favorito.getIdJuego() + " no existe en el catálogo.");
        }
        
        // la regla de negocio es Validar que el usuario no tenga ya este juego en sus favoritos
        List<JuegoFavorito> favoritosUsuario = repository.findByIdUsuario(favorito.getIdUsuario());
        boolean yaEsFavorito = favoritosUsuario.stream()
                .anyMatch(f -> f.getIdJuego().equals(favorito.getIdJuego()));
                
        if (yaEsFavorito) {
            throw new IllegalArgumentException("El videojuego ya se encuentra en la lista de favoritos de este usuario.");
        }
        
        return repository.save(favorito);
    }

    // Recupera la lista completa de juegos marcados como favoritos por un usuario específico
    public List<JuegoFavorito> obtenerFavoritosPorUsuario(Integer idUsuario) {
        List<JuegoFavorito> favoritos = repository.findByIdUsuario(idUsuario);
        if (favoritos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron videojuegos en la lista de favoritos para el usuario con ID: " + idUsuario);
        }
        return favoritos;
    }
}