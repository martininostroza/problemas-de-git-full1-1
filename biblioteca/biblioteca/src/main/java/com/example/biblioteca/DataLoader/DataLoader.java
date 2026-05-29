package com.example.biblioteca.DataLoader;


import com.example.biblioteca.model.BibliotecaModel;
import com.example.biblioteca.repository.BibliotecaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(BibliotecaRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                // Sincronizamos los juegos con los usuarios reales del micro de Usuarios:
                // ID 1 = Ana García -> Le asignamos Fortnite
                repository.save(new BibliotecaModel(null, 1, "Fortnite"));
                
                // ID 2 = Carlos Pérez -> Le asignamos The Walking Dead
                repository.save(new BibliotecaModel(null, 2, "The Walking Dead"));
                
                // ID 3 = Laura Martínez -> Le asignamos Mortal Kombat X
                repository.save(new BibliotecaModel(null, 3, "Mortal Kombat X"));

            }
        };
    }
}