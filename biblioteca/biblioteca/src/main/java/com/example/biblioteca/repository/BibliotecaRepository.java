package com.example.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.model.BibliotecaModel;
import java.util.List;

// El REPOSITORIO conecta con la base de datos db_biblioteca.
@Repository
public interface BibliotecaRepository extends JpaRepository<BibliotecaModel, Integer> {
    
    // Método clave para que el usuario pueda ver su lista de juegos adquiridos
    List<BibliotecaModel> findByUsuarioId(Integer usuarioId);
}