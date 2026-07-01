package com.example.biblioteca.service;

import com.example.biblioteca.model.DatosArticuloCarrito;
import com.example.biblioteca.model.EntradaBiblioteca;
import com.example.biblioteca.model.PeticionNotificacion;
import com.example.biblioteca.model.RespuestaBiblioteca;
import com.example.biblioteca.repository.BibliotecaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BibliotecaServiceTest {

    @Mock
    private BibliotecaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BibliotecaService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void comprarCarrito_itemsNulo_lanzaIllegalArgumentException() {
        int idUsuario = 1;

        when(restTemplate.getForObject(anyString(), eq(DatosArticuloCarrito[].class))).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.comprarCarrito(idUsuario));

        verify(repository, never()).findByIdUsuario(anyInt());
        verify(repository, never()).save(any(EntradaBiblioteca.class));
        verify(restTemplate, never()).postForObject(anyString(), any(PeticionNotificacion.class), eq(String.class));
    }

    @Test
    void comprarCarrito_itemsVacio_lanzaIllegalArgumentException() {
        int idUsuario = 1;

        when(restTemplate.getForObject(anyString(), eq(DatosArticuloCarrito[].class))).thenReturn(new DatosArticuloCarrito[0]);

        assertThrows(IllegalArgumentException.class, () -> service.comprarCarrito(idUsuario));

        verify(repository, never()).findByIdUsuario(anyInt());
        verify(repository, never()).save(any(EntradaBiblioteca.class));
        verify(restTemplate, never()).postForObject(anyString(), any(PeticionNotificacion.class), eq(String.class));
    }

    @Test
    void comprarCarrito_postNotificacionFalla_noRompeRetorno() {
        int idUsuario = 10;

        DatosArticuloCarrito item = new DatosArticuloCarrito();
        item.setIdJuego(200);

        when(restTemplate.getForObject(anyString(), eq(DatosArticuloCarrito[].class)))
                .thenReturn(new DatosArticuloCarrito[]{item});

        when(repository.findByIdUsuario(idUsuario)).thenReturn(List.of());

        EntradaBiblioteca guardado = new EntradaBiblioteca();
        guardado.setIdEntradaBiblioteca(777);
        guardado.setIdUsuario(idUsuario);
        guardado.setIdJuego(200);
        when(repository.save(any(EntradaBiblioteca.class))).thenReturn(guardado);

        when(restTemplate.postForObject(anyString(), any(PeticionNotificacion.class), eq(String.class)))
                .thenThrow(new RuntimeException("downstream error"));

        List<RespuestaBiblioteca> respuestas = service.comprarCarrito(idUsuario);

        assertEquals(1, respuestas.size());
        assertEquals("Guardado con éxito", respuestas.get(0).getEstado());
    }

    @Test
    void obtenerBibliotecaPorUsuario_empty_lanzaIllegalArgumentException() {
        int idUsuario = 99;
        when(repository.findByIdUsuario(idUsuario)).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> service.obtenerBibliotecaPorUsuario(idUsuario));
    }

}

