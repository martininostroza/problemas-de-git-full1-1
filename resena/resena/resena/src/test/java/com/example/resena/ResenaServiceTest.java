package com.example.resena;

import com.example.resena.Model.Resena;
import com.example.resena.Repository.ResenaRepository;
import com.example.resena.Service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ResenaService service;

    @BeforeEach
    void setUp() {
    }

    // 1) OK: guardarResena - juego existe, usuario no reseñó, guarda correctamente
    @Test
    void guardarResena_ok() {
        Resena input = new Resena(null, 10, 100, "Buen juego", 5);
        Resena saved = new Resena(1, 10, 100, "Buen juego", 5);

        when(restTemplate.getForObject(any(String.class), eq(Object.class))).thenReturn(new Object());
        when(repository.findByIdJuego(eq(100))).thenReturn(List.of());
        when(repository.save(any(Resena.class))).thenReturn(saved);

        Resena result = service.guardarResena(input);

        assertNotNull(result);
        assertEquals(1, result.getIdResena());
        assertEquals(10, result.getIdUsuario());
        assertEquals(100, result.getIdJuego());
        assertEquals("Buen juego", result.getComentario());
        assertEquals(5, result.getCalificacion());

        verify(restTemplate, times(1)).getForObject(any(String.class), eq(Object.class));
        verify(repository, times(1)).findByIdJuego(eq(100));
        verify(repository, times(1)).save(eq(input));
    }

    // 2) Error: juego no existe => IllegalArgumentException
    @Test
    void guardarResena_juegoNoExiste_lanzaIllegalArgumentException() {
        Resena input = new Resena(null, 10, 999, "Buen juego", 5);

        when(restTemplate.getForObject(any(String.class), eq(Object.class)))
                .thenThrow(new RuntimeException("404"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.guardarResena(input));

        assertTrue(ex.getMessage().contains("no existe en el catálogo"));
        assertTrue(ex.getMessage().contains("999"));

        verify(repository, never()).findByIdJuego(anyInt());
        verify(repository, never()).save(any());
    }

    // 3) Error: usuario ya reseñó el juego => IllegalArgumentException
    @Test
    void guardarResena_usuarioYaResenado_lanzaIllegalArgumentException() {
        Resena input = new Resena(null, 10, 100, "Buen juego", 5);

        when(restTemplate.getForObject(any(String.class), eq(Object.class))).thenReturn(new Object());

        Resena existing = new Resena(2, 10, 100, "Otra reseña", 4);
        when(repository.findByIdJuego(eq(100))).thenReturn(List.of(existing));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.guardarResena(input));

        assertTrue(ex.getMessage().contains("Ya has publicado una reseña"));

        verify(repository, times(1)).findByIdJuego(eq(100));
        verify(repository, never()).save(any());
    }

    // 4) OK: obtenerResenasPorJuego - lista no vacía
    @Test
    void obtenerResenasPorJuego_ok() {
        Integer idJuego = 100;
        Resena r1 = new Resena(1, 10, 100, "Comentario 1", 3);
        Resena r2 = new Resena(2, 11, 100, "Comentario 2", 5);

        when(repository.findByIdJuego(eq(idJuego))).thenReturn(List.of(r1, r2));

        List<Resena> result = service.obtenerResenasPorJuego(idJuego);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdResena());
        assertEquals(2, result.get(1).getIdResena());

        verify(repository, times(1)).findByIdJuego(eq(idJuego));
    }

    // 5) Error: obtenerResenasPorJuego - lista vacía => IllegalArgumentException
    @Test
    void obtenerResenasPorJuego_sinResenas_lanzaIllegalArgumentException() {
        Integer idJuego = 123;

        when(repository.findByIdJuego(eq(idJuego))).thenReturn(List.of());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.obtenerResenasPorJuego(idJuego));

        assertTrue(ex.getMessage().contains("No se encontraron reseñas"));
        assertTrue(ex.getMessage().contains(idJuego.toString()));

        verify(repository, times(1)).findByIdJuego(eq(idJuego));
    }
}

