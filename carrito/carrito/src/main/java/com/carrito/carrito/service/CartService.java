package com.carrito.carrito.service;

import com.carrito.carrito.model.CartItem;
import com.carrito.carrito.model.CartResponse;
import com.carrito.carrito.model.GameData;
import com.carrito.carrito.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyección correcta del Bean administrado

    // Obtiene los artículos del carrito de un usuario y los cruza con los datos de catálogo usando llamadas HTTP
    public List<CartResponse> obtenerCarrito(Integer idUsuario) {
        List<CartItem> items = repository.findByIdUsuario(idUsuario);
        List<CartResponse> respuestas = new ArrayList<>();
        
        for (CartItem item : items) {
            String url = "http://localhost:8081/catalogo/buscar-id/" + item.getIdGame();
            GameData juego = null;
            
            try {
                juego = restTemplate.getForObject(url, GameData.class);
            } catch (Exception e) {
                // Si el catálogo no responde o el juego fue eliminado, se maneja la información de forma segura
            }
            
            CartResponse respuesta = new CartResponse();
            respuesta.setIdCartItem(item.getIdCartItem());
            respuesta.setIdUsuario(item.getIdUsuario());
            respuesta.setCantidad(item.getCantidad());
            respuesta.setJuego(juego);
            
            respuestas.add(respuesta);
        }
        
        return respuestas;
    }

    // Almacena un nuevo artículo o actualización en la base de datos local del carrito aplicando reglas de negocio
    public CartItem agregar(CartItem item) {
        // COMUNICACIÓN / REGLA DE NEGOCIO: Validar que el juego exista realmente en el catálogo antes de agregarlo
        String urlCatalogo = "http://localhost:8081/catalogo/buscar-id/" + item.getIdGame();
        try {
            restTemplate.getForObject(urlCatalogo, GameData.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se puede agregar al carrito: El videojuego con ID " + item.getIdGame() + " no existe en el catálogo.");
        }

        // la regla de negocio es Si el juego ya está en el carrito de este usuario, incrementar la cantidad en lugar de duplicar la fila
        List<CartItem> itemsExistentes = repository.findByIdUsuario(item.getIdUsuario());
        Optional<CartItem> itemDuplicado = itemsExistentes.stream()
                .filter(i -> i.getIdGame().equals(item.getIdGame()))
                .findFirst();

        if (itemDuplicado.isPresent()) {
            CartItem existente = itemDuplicado.get();
            existente.setCantidad(existente.getCantidad() + item.getCantidad());
            return repository.save(existente);
        }

        return repository.save(item);
    }

    // Verifica la existencia de un artículo por su ID y lanza una excepción si no existe
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: No se encontró ningún artículo en el carrito con el ID: " + id);
        }
        repository.deleteById(id);
    }
}