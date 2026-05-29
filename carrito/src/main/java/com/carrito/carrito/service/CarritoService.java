package com.carrito.carrito.service;

import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.model.ItemCarrito;
import com.carrito.carrito.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository repository;

    public Carrito agregarAlCarrito(Integer idUsuario, ItemCarrito nuevoItem) {
        Carrito carrito = repository.findByIdUsuarioAndEstado(idUsuario, "PENDIENTE")
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setIdUsuario(idUsuario);
                    nuevo.setEstado("PENDIENTE");
                    nuevo.setTotal(BigDecimal.ZERO);
                    return nuevo;
                });

        carrito.getItems().add(nuevoItem);

        recalcularTotal(carrito);

        return repository.save(carrito);
    }

    private void recalcularTotal(Carrito carrito) {
        BigDecimal suma = carrito.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrito.setTotal(suma);
    }

    public Optional<Carrito> pagarCarrito(Integer idUsuario) {
        return repository.findByIdUsuarioAndEstado(idUsuario, "PENDIENTE")
                .map(carrito -> {
                    carrito.setEstado("PAGADO");
                    return repository.save(carrito);
                });
    }
}