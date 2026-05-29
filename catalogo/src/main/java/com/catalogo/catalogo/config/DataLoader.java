package com.catalogo.catalogo.config;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(GameRepository repository) {
        return args -> {
            crearJuegoSiNoExiste(repository, 
                "Super Mario Bros", 
                "Un clásico de plataformas de Nintendo con saltos y aventuras en el Reino Champiñón.", 
                "29.99", "Plataformas");

            crearJuegoSiNoExiste(repository, 
                "Mortal Kombat X", 
                "Un juego de lucha visceral con personajes icónicos, fatalidades y combates 1 contra 1.", 
                "39.99", "Lucha");

            crearJuegoSiNoExiste(repository, 
                "Resident Evil 4", 
                "Survival horror con acción intensa y la misión de rescatar a la hija del presidente.", 
                "34.99", "Horror");

            crearJuegoSiNoExiste(repository, 
                "The Walking Dead", 
                "Aventura narrativa basada en la serie, centrada en decisiones y supervivencia.", 
                "24.99", "Aventura");

            crearJuegoSiNoExiste(repository, 
                "Fortnite", 
                "Battle royale multijugador con construcción, acción y partidas rápidas.", 
                "0.00", "Battle Royale");
        };
    }

    private void crearJuegoSiNoExiste(GameRepository repo, 
                                      String titulo, 
                                      String descripcion, 
                                      String precio, 
                                      String categoria) {
        
        // Buscamos si el juego ya existe por su título
        List<Game> games = repo.findByTituloContainingIgnoreCase(titulo);

        if (games.isEmpty()) {
            Game nuevoJuego = new Game();
            nuevoJuego.setTitulo(titulo);
            nuevoJuego.setDescripcion(descripcion);
            nuevoJuego.setPrecio(new BigDecimal(precio));
            nuevoJuego.setCategoria(categoria);
            repo.save(nuevoJuego);
            System.out.println("Cargado juego: " + titulo);
        } else {
            System.out.println("El juego '" + titulo + "' ya existe en el catálogo. Saltando...");
        }
    }
}