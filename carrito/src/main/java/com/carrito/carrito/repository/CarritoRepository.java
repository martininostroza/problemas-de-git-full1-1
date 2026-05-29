package com.carrito.carrito.repository;

import com.carrito.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Optional<Carrito> findByIdUsuarioAndEstado(Integer idUsuario, String estado);
    
}