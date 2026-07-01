package com.catalogo.catalogo.service;

import com.catalogo.catalogo.model.Game;
import com.catalogo.catalogo.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository repository;

    @InjectMocks
    private GameService service;

    private Game game1;
    private Game gameUpdated;

    @BeforeEach
    void setUp() {
        game1 = new Game();
        game1.setIdGame(1);
        game1.setTitulo("Super Mario Bros");
        game1.setDescripcion("Un clásico");
        game1.setPrecio(new BigDecimal("29.99"));
        game1.setCategoria("Plataformas");

        gameUpdated = new Game();
        gameUpdated.setTitulo("Super Mario Bros 2");
        gameUpdated.setDescripcion("Actualizado");
        gameUpdated.setPrecio(new BigDecimal("39.99"));
        gameUpdated.setCategoria("Plataformas");
    }

    @Test
    void findAll_returnsListFromRepository() {
        Game game2 = new Game();
        game2.setIdGame(2);
        game2.setTitulo("Mortal Kombat X");
        game2.setDescripcion("Lucha");
        game2.setPrecio(new BigDecimal("39.99"));
        game2.setCategoria("Lucha");

        when(repository.findAll()).thenReturn(List.of(game1, game2));

        List<Game> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Super Mario Bros", result.get(0).getTitulo());
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById_existingId_returnsGame() {
        when(repository.findById(1)).thenReturn(Optional.of(game1));

        Game result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdGame());
        assertEquals("Super Mario Bros", result.getTitulo());
        verify(repository, times(1)).findById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById_missingId_throwsIllegalArgumentException() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.findById(999)
        );

        assertTrue(ex.getMessage().contains("ID: 999"));
        verify(repository, times(1)).findById(999);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void update_existingId_updatesFieldsAndSaves() {
        when(repository.findById(1)).thenReturn(Optional.of(game1));

        when(repository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = service.update(1, gameUpdated);

        assertNotNull(result);
        assertEquals(1, result.getIdGame());
        assertEquals("Super Mario Bros 2", result.getTitulo());
        assertEquals("Actualizado", result.getDescripcion());
        assertEquals(new BigDecimal("39.99"), result.getPrecio());
        assertEquals("Plataformas", result.getCategoria());

        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(captor.capture());
        Game saved = captor.getValue();
        assertEquals("Super Mario Bros 2", saved.getTitulo());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void delete_missingId_throwsIllegalArgumentException() {
        when(repository.existsById(123)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.delete(123)
        );

        assertTrue(ex.getMessage().contains("ID 123"));
        verify(repository, times(1)).existsById(123);
        verify(repository, never()).deleteById(anyInt());
        verifyNoMoreInteractions(repository);
    }
}

