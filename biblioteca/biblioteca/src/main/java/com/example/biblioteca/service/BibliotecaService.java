package com.example.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.biblioteca.model.BibliotecaModel;
import com.example.biblioteca.repository.BibliotecaRepository;
import java.util.List;

// El SERVICIO ejecuta la orden de añadir juegos proveniente del Carrito.
@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository repository;

    // Registra el juego en la cuenta del usuario
    public BibliotecaModel agregarALaBiblioteca(BibliotecaModel registro) {
        return repository.save(registro);
    }

    // Devuelve la colección de un usuario
    public List<BibliotecaModel> obtenerBibliotecaDelUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}