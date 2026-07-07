package com.example.soporte.service;

import com.example.soporte.dto.TicketResponseDTO;
import com.example.soporte.dto.UsuarioDTO;
import com.example.soporte.model.TicketSoporte;
import com.example.soporte.repository.TicketSoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketSoporteService {

    @Autowired
    private TicketSoporteRepository repository;

    @Autowired
    private RestTemplate restTemplate; 

    // POST: Guarda el ticket directo validando contra el microservicio de usuarios
    public TicketSoporte crearTicket(TicketSoporte ticket) {
        String url = "http://localhost:8084/usuarios/buscar-id/" + ticket.getIdUsuario();
        
        try {
            // Verifica de forma síncrona si el usuario existe
            restTemplate.getForObject(url, Object.class);
            ticket.setEstado("Abierto"); 
            return repository.save(ticket);
        } catch (Exception e) {
            // si falla la comunicación o no existe, retorna null para que el controller avise
            return null;
        }
    }

    
    public List<TicketResponseDTO> obtenerTicketsPorUsuarioConDatos(Integer idUsuario) {
 
        List<TicketSoporte> ticketsEntity = repository.findByIdUsuario(idUsuario); 
        
      
        String url = "http://localhost:8084/usuarios/buscar-id/" + idUsuario;
        
        // Consumimos el endpoint de usuarios. Si no encuentra nada o está apagado, arrojará el error real.
        UsuarioDTO datosUsuario = restTemplate.getForObject(url, UsuarioDTO.class);


        return ticketsEntity.stream().map(ticket -> {
            TicketResponseDTO dto = new TicketResponseDTO();
            dto.setIdTicket(ticket.getIdTicket());
            dto.setAsunto(ticket.getAsunto());
            dto.setDescripcion(ticket.getDescripcion());
            dto.setEstado(ticket.getEstado());
            dto.setUsuario(datosUsuario); 
            return dto;
        }).collect(Collectors.toList());
    }

    // Busca por id de usuario plano (Mantenido por compatibilidad)
    public List<TicketSoporte> obtenerTicketsPorUsuario(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    // Busca un ticket por su propio id
    public Optional<TicketSoporte> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // Elimina por id directo
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }
}