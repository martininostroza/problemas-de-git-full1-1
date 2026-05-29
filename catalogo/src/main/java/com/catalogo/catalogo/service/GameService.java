package com.catalogo.catalogo.service;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public List<Game> findAll() {
        return repository.findAll();
    }

    public Optional<Game> findById(Integer id) {
        return repository.findById(id);
    }

    public Game agregar(Game game) {
        return repository.save(game);
    }

    public Optional<Game> update(Integer id, Game gameUpdate) {
        return repository.findById(id)
                .map(gameExisting -> {
                    gameExisting.setTitulo(gameUpdate.getTitulo());
                    gameExisting.setDescripcion(gameUpdate.getDescripcion());
                    gameExisting.setPrecio(gameUpdate.getPrecio());
                    gameExisting.setCategoria(gameUpdate.getCategoria());
                    return repository.save(gameExisting);
                });
    }

    public boolean delete(Integer id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
