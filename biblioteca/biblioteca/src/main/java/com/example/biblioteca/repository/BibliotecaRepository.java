package com.example.biblioteca.repository;

import com.example.biblioteca.model.EntradaBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BibliotecaRepository extends JpaRepository<EntradaBiblioteca, Integer> {

    List<EntradaBiblioteca> findByIdUsuario(Integer idUsuario);
}