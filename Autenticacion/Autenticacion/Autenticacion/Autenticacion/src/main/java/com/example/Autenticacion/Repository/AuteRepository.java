package com.example.Autenticacion.Repository;

import com.example.Autenticacion.Model.AuteUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuteRepository extends JpaRepository<AuteUsuario, Integer> {
    Optional<AuteUsuario> findByEmail(String email);
}