package com.carrito.carrito.service;

import com.carrito.carrito.model.CartItem;
import com.carrito.carrito.model.CartResponse;
import com.carrito.carrito.model.GameData;
import com.carrito.carrito.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartService service;

    private GameData game;

    @BeforeEach
    void setUp() {
        game = new GameData();
    }

    @Test
    void obtenerCarrito_debeRetornarRespuestasConJuegoCuandoCatalogoResponde() {
        Integer idUsuario = 10;

        CartItem item1 = new CartItem();
        item1.setIdCartItem(1);
        item1.setIdUsuario(idUsuario);
        item1.setIdGame(100);
        item1.setCantidad(2);

        CartItem item2 = new CartItem();
        item2.setIdCartItem(2);
        item2.setIdUsuario(idUsuario);
        item2.setIdGame(200);
        item2.setCantidad(1);

        when(repository.findByIdUsuario(idUsuario)).thenReturn(Arrays.asList(item1, item2));
        when(restTemplate.getForObject(anyString(), eq(GameData.class))).thenReturn(game);


        List<CartResponse> respuestas = service.obtenerCarrito(idUsuario);

        assertThat(respuestas).hasSize(2);

        CartResponse r1 = respuestas.get(0);
        assertThat(r1.getIdCartItem()).isEqualTo(1);
        assertThat(r1.getIdUsuario()).isEqualTo(idUsuario);
        assertThat(r1.getCantidad()).isEqualTo(2);
        assertThat(r1.getJuego()).isSameAs(game);

        CartResponse r2 = respuestas.get(1);
        assertThat(r2.getIdCartItem()).isEqualTo(2);
        assertThat(r2.getIdUsuario()).isEqualTo(idUsuario);
        assertThat(r2.getCantidad()).isEqualTo(1);
        assertThat(r2.getJuego()).isSameAs(game);

        verify(repository, times(1)).findByIdUsuario(idUsuario);
        verify(restTemplate, times(2)).getForObject(anyString(), eq(GameData.class));

    }

    @Test
    void obtenerCarrito_debeRetornarRespuestasConJuegoNullCuandoCatalogoFalla() {
        Integer idUsuario = 10;

        CartItem item1 = new CartItem();
        item1.setIdCartItem(1);
        item1.setIdUsuario(idUsuario);
        item1.setIdGame(100);
        item1.setCantidad(2);

        when(repository.findByIdUsuario(idUsuario)).thenReturn(Collections.singletonList(item1));
        when(restTemplate.getForObject(anyString(), eq(GameData.class)))
                .thenThrow(new RuntimeException("Catalogo no responde"));


        List<CartResponse> respuestas = service.obtenerCarrito(idUsuario);

        assertThat(respuestas).hasSize(1);
        CartResponse r1 = respuestas.get(0);
        assertThat(r1.getIdCartItem()).isEqualTo(1);
        assertThat(r1.getIdUsuario()).isEqualTo(idUsuario);
        assertThat(r1.getCantidad()).isEqualTo(2);
        assertThat(r1.getJuego()).isNull();

        verify(repository, times(1)).findByIdUsuario(idUsuario);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(GameData.class));

    }

    @Test
    void agregar_debeLanzarIllegalArgumentExceptionCuandoCatalogoNoEncuentraJuego() {
        CartItem item = new CartItem();
        item.setIdUsuario(10);
        item.setIdGame(999);
        item.setCantidad(1);

        when(restTemplate.getForObject(anyString(), eq(GameData.class)))
                .thenThrow(new RuntimeException("No encontrado"));


        assertThatThrownBy(() -> service.agregar(item))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no existe en el catálogo")
                .hasMessageContaining("999");

        verify(repository, never()).findByIdUsuario(anyInt());
        verify(repository, never()).save(any(CartItem.class));
    }

    @Test
    void agregar_debeActualizarCantidadSiElJuegoYaExisteEnElCarrito() {
        CartItem nuevo = new CartItem();
        nuevo.setIdUsuario(10);
        nuevo.setIdGame(100);
        nuevo.setCantidad(3);

        when(restTemplate.getForObject(anyString(), eq(GameData.class))).thenReturn(game);


        CartItem existente = new CartItem();
        existente.setIdCartItem(55);
        existente.setIdUsuario(10);
        existente.setIdGame(100);
        existente.setCantidad(2);

        when(repository.findByIdUsuario(10)).thenReturn(Collections.singletonList(existente));
        when(repository.save(any(CartItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem resultado = service.agregar(nuevo);

        assertThat(resultado.getIdCartItem()).isEqualTo(55);
        assertThat(resultado.getIdUsuario()).isEqualTo(10);
        assertThat(resultado.getIdGame()).isEqualTo(100);
        assertThat(resultado.getCantidad()).isEqualTo(5);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getCantidad()).isEqualTo(5);
    }

    @Test
    void delete_debeLanzarIllegalArgumentExceptionSiNoExisteElIdEnRepository() {
        Integer id = 123;
        when(repository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No se puede eliminar")
                .hasMessageContaining(String.valueOf(id));

        verify(repository, never()).deleteById(anyInt());
    }
}

