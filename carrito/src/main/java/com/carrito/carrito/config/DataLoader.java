package com.carrito.carrito.config;

import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.model.ItemCarrito;
import com.carrito.carrito.repository.CarritoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(CarritoRepository repository) {
        return args -> {
            
            crearCarritoInicialSiNoExiste(repository, 1, 1, "Elden Ring", 1, "59.99");
            
            crearCarritoInicialSiNoExiste(repository, 2, 3, "Minecraft", 2, "29.99");

            System.out.println("Proceso de carga de datos iniciales de Carrito finalizado.");
        };
    }

    private void crearCarritoInicialSiNoExiste(CarritoRepository repo, 
                                               Integer idUsuario, 
                                               Integer idGame, 
                                               String titulo, 
                                               int cantidad, 
                                               String precioUnitario) {
                                               
        // Buscamos si el usuario ya tiene un carrito "PENDIENTE"
        Optional<Carrito> carritoOpt = repo.findByIdUsuarioAndEstado(idUsuario, "PENDIENTE");
        
        if (carritoOpt.isEmpty()) {
            // 1. Crear el ítem del juego
            ItemCarrito item = new ItemCarrito();
            item.setIdGame(idGame);
            item.setTitulo(titulo);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(new BigDecimal(precioUnitario));

            // 2. Crear el carrito
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setIdUsuario(idUsuario);
            nuevoCarrito.setEstado("PENDIENTE");
            nuevoCarrito.getItems().add(item);
            
            // 3. Calcular el total inicial
            BigDecimal total = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
            nuevoCarrito.setTotal(total);
            
            repo.save(nuevoCarrito);
            System.out.println("Cargado carrito inicial para Usuario ID: " + idUsuario + " con el juego: " + titulo);
        } else {
            System.out.println("El usuario " + idUsuario + " ya tiene un carrito activo. Saltando...");
        }
    }
}