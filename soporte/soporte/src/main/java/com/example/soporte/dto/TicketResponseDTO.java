package com.example.soporte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Integer idTicket;
    private String asunto;
    private String descripcion;
    private String estado;
    private UsuarioDTO usuario; 
}