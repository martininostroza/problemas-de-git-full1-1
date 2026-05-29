package com.catalogo.catalogo.repository;

import com.catalogo.catalogo.model.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findById(Integer id);

    List<Game> findByTituloContainingIgnoreCase(String titulo);

    List<Game> findByCategoriaIgnoreCase(String categoria);

    List<Game> findByPrecioBetween(Double min, Double max);

}
