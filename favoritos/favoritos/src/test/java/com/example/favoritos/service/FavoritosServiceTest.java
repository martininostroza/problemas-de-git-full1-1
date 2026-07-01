package com.example.favoritos.service;

import com.example.favoritos.model.DatosJuegoCatalogo;
import com.example.favoritos.model.JuegoFavorito;
import com.example.favoritos.repository.FavoritosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritosServiceTest {

    @Mock
    private FavoritosRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FavoritosService service;

    private JuegoFavorito favorito;

    @BeforeEach
    void setup() {
        favorito = new JuegoFavorito(null, 1, 10);
    }

    @Test
    void agregarAFavoritos_OK_guardaCuandoExisteYNoEsDuplicado() {
        DatosJuegoCatalogo catalogo = new DatosJuegoCatalogo();
        catalogo.setIdGame(10);
        catalogo.setTitulo("Juego");

        when(restTemplate.getForObject(anyString(), eq(DatosJuegoCatalogo.class))).thenReturn(catalogo);
        when(repository.findByIdUsuario(favorito.getIdUsuario())).thenReturn(Collections.emptyList());

        JuegoFavorito guardado = new JuegoFavorito(99, favorito.getIdUsuario(), favorito.getIdJuego());
        when(repository.save(favorito)).thenReturn(guardado);

        JuegoFavorito resultado = service.agregarAFavoritos(favorito);

        assertNotNull(resultado);
        assertEquals(99, resultado.getIdJuegoFavorito());
        assertEquals(1, resultado.getIdUsuario());
        assertEquals(10, resultado.getIdJuego());

        verify(repository, times(1)).findByIdUsuario(favorito.getIdUsuario());
        verify(repository, times(1)).save(favorito);
    }

    @Test
    void agregarAFavoritos_error_siRestTemplateFalla_elJuegoNoExiste() {
        when(restTemplate.getForObject(anyString(), eq(DatosJuegoCatalogo.class)))
                .thenThrow(new RuntimeException("catalogo down"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.agregarAFavoritos(favorito)
        );

        assertTrue(ex.getMessage().contains("no existe en el catálogo"));
        verify(repository, never()).save(any());
    }

    @Test
    void agregarAFavoritos_error_siEsDuplicado_enFavoritosUsuario() {
        DatosJuegoCatalogo catalogo = new DatosJuegoCatalogo();
        catalogo.setIdGame(10);
        catalogo.setTitulo("Juego");

        when(restTemplate.getForObject(anyString(), eq(DatosJuegoCatalogo.class))).thenReturn(catalogo);

        JuegoFavorito yaGuardado = new JuegoFavorito(50, favorito.getIdUsuario(), favorito.getIdJuego());
        when(repository.findByIdUsuario(favorito.getIdUsuario()))
                .thenReturn(List.of(yaGuardado));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.agregarAFavoritos(favorito)
        );

        assertTrue(ex.getMessage().contains("ya se encuentra"));
        verify(repository, times(1)).findByIdUsuario(favorito.getIdUsuario());
        verify(repository, never()).save(any());
    }

    @Test
    void obtenerFavoritosPorUsuario_ok_devuelveListaNoVacia() {
        List<JuegoFavorito> favoritos = List.of(
                new JuegoFavorito(1, 1, 10),
                new JuegoFavorito(2, 1, 20)
        );
        when(repository.findByIdUsuario(1)).thenReturn(favoritos);

        List<JuegoFavorito> resultado = service.obtenerFavoritosPorUsuario(1);

        assertEquals(2, resultado.size());
        assertEquals(favoritos, resultado);
        verify(repository, times(1)).findByIdUsuario(1);
    }

    @Test
    void obtenerFavoritosPorUsuario_error_siListaVacia() {
        when(repository.findByIdUsuario(999)).thenReturn(Collections.emptyList());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.obtenerFavoritosPorUsuario(999)
        );

        assertTrue(ex.getMessage().contains("No se encontraron"));
        verify(repository, times(1)).findByIdUsuario(999);
    }
}

