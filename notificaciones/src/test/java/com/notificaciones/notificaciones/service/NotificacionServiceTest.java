package com.notificaciones.notificaciones.service;

import com.notificaciones.notificaciones.model.Notificacion;
import com.notificaciones.notificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService service;

    private Notificacion baseNotificacion;

    @BeforeEach
    void setUp() {
        baseNotificacion = new Notificacion();
        baseNotificacion.setIdNotificacion(10);
        baseNotificacion.setUsuarioId(5);
        baseNotificacion.setMensaje("Hola");
        baseNotificacion.setLeido(false);
    }

    @Test
    void findById_whenExists_returnsNotificacion() {
        when(repository.findById(10)).thenReturn(Optional.of(baseNotificacion));

        Notificacion result = service.findById(10);

        assertNotNull(result);
        assertEquals(Integer.valueOf(10), result.getIdNotificacion());
        
        // CORREGIDO: Cambiado de isLeido() a getLeido()
        assertFalse(result.getLeido()); 
        
        verify(repository, times(1)).findById(10);
    }

    @Test
    void findById_whenNotExists_throwsIllegalArgumentException() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findById(999));

        assertTrue(ex.getMessage().contains("No se encontró ninguna notificación"));
        verify(repository, times(1)).findById(999);
    }

    @Test
    void save_forcesLeidoFalse_beforeSaving() {
        Notificacion input = new Notificacion();
        input.setUsuarioId(1);
        input.setMensaje("Mensaje");
        input.setLeido(true);

        when(repository.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Notificacion result = service.save(input);

        assertNotNull(result);
        
        // CORREGIDO: Cambiado de isLeido() a getLeido()
        assertFalse(result.getLeido(), "Se esperaba que save forzara leido=false");
        
        verify(repository, times(1)).save(input);
    }

    @Test
    void delete_whenExists_deletesById() {
        when(repository.existsById(10)).thenReturn(true);

        service.delete(10);

        verify(repository, times(1)).existsById(10);
        verify(repository, times(1)).deleteById(10);
    }
}