package com.example.soporte.service;

import com.example.soporte.model.TicketSoporte;
import com.example.soporte.repository.TicketSoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class TicketSoporteService {

    @Autowired
    private TicketSoporteRepository repository;

    @Autowired
    private RestTemplate restTemplate; 

    // guarda el ticket directo
    public TicketSoporte crearTicket(TicketSoporte ticket) {
        String url = "http://localhost:8084/usuarios/buscar-id/" + ticket.getIdUsuario();
        
        try {
            // verifica de forma síncrona si el usuario existe
            restTemplate.getForObject(url, Object.class);
            ticket.setEstado("Abierto"); 
            return repository.save(ticket);
        } catch (Exception e) {
            // si falla la comunicación o no existe, retorna null para que el controller avise
            return null;
        }
    }

    // busca por id de usuario
    public List<TicketSoporte> obtenerTicketsPorUsuario(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    // busca un ticket por su propio id usando optional como el profe
    public Optional<TicketSoporte> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // elimina por id directo
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }
}