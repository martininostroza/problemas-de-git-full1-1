package com.catalogo.catalogo.service;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public List<Game> findAll() {
        return repository.findAll();
    }

    // EXCEPCIÓN DIRECTA: Lanza error de inmediato si el ID del videojuego no es válido
    public Game findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún videojuego en el catálogo con el ID: " + id));
    }

    public Game agregar(Game game) {
        return repository.save(game);
    }

    // CONTROL TOTAL DESDE SERVICE: Actualiza o rompe el flujo lanzando la excepción controlada
    public Game update(Integer id, Game gameUpdate) {
        Game gameExisting = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar: El videojuego con ID " + id + " no existe en el catálogo."));

        gameExisting.setTitulo(gameUpdate.getTitulo());
        gameExisting.setDescripcion(gameUpdate.getDescripcion());
        gameExisting.setPrecio(gameUpdate.getPrecio());
        gameExisting.setCategoria(gameUpdate.getCategoria());
        
        return repository.save(gameExisting);
    }

    // CAPA DEL MODELO PROTEGIDA: Eliminación limpia sin retornar booleanos
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: El videojuego con ID " + id + " no existe en el catálogo.");
        }
        repository.deleteById(id);
    }
}
