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

                repository.save(new BibliotecaModel(null, 1, "Fortnite"));
                repository.save(new BibliotecaModel(null, 2, "The Walking Dead"));
                repository.save(new BibliotecaModel(null, 3, "Mortal Kombat X"));

            }
        };
    }
}