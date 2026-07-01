package com.example.soporte.service;

import com.example.soporte.model.TicketSoporte;
import com.example.soporte.repository.TicketSoporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketSoporteServiceTest {

    @Mock
    private TicketSoporteRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TicketSoporteService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void obtenerTicketsPorUsuario_debeDelegarEnRepository() {
        Integer idUsuario = 5;
        List<TicketSoporte> expected = List.of(
                new TicketSoporte(1, idUsuario, "A", "B", "Abierto"),
                new TicketSoporte(2, idUsuario, "C", "D", "Abierto")
        );

        when(repository.findByIdUsuario(idUsuario)).thenReturn(expected);

        List<TicketSoporte> result = service.obtenerTicketsPorUsuario(idUsuario);

        assertEquals(expected, result);
        verify(repository, times(1)).findByIdUsuario(idUsuario);
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptionalConTicket() {
        Integer idTicket = 99;
        TicketSoporte ticket = new TicketSoporte(idTicket, 1, "Asunto", "Descripcion", "Abierto");

        when(repository.findById(idTicket)).thenReturn(Optional.of(ticket));

        Optional<TicketSoporte> result = service.buscarPorId(idTicket);

        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
        verify(repository, times(1)).findById(idTicket);
    }

    @Test
    void eliminarPorId_debeDelegarEnRepositoryDeleteById() {
        Integer idTicket = 123;

        doNothing().when(repository).deleteById(idTicket);

        service.eliminarPorId(idTicket);

        verify(repository, times(1)).deleteById(idTicket);
    }
}

