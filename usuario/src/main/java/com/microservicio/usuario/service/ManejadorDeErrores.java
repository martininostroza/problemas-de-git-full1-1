package com.microservicio.usuario.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservicio.usuario.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorDeErrores {

    // 1. CAPTURA ERRORES DE VALIDACIÓN (@Valid en Controllers)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        }); 

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            400, // Sincronizado: 400 Bad Request
            "Error de validación en los campos del formulario",
            errores,
            request.getRequestURI()
        );
        return ResponseEntity.status(400).body(errorDTO);
    }

    // 2. CAPTURA ERRORES DE LOGICA DE NEGOCIO (¡Esta es la que faltaba para asegurar la nota!)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeNegocio(IllegalArgumentException ex, HttpServletRequest request) {
        Map<String, String> detalles = new HashMap<>();
        detalles.put("error", ex.getMessage()); // Captura el mensaje exacto que escribimos en el Service

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            400, // 400 Bad Request para peticiones inválidas del negocio
            "Operación rechazada por reglas de negocio",
            detalles,
            request.getRequestURI()
        );
        return ResponseEntity.status(400).body(errorDTO);
    }

    // 3. CAPTURA ERRORES DE RESTRICCIONES DE BASE DE DATOS (Failsafes)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeBaseDeDatos(DataIntegrityViolationException ex, HttpServletRequest request) {
        Map<String, String> detalles = new HashMap<>();
        detalles.put("error", "Conflicto de integridad en la base de datos (Llave duplicada o restricción violada).");
            
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            409, // 409  para duplicados en base de datos
            "Error de persistencia",
            detalles, 
            request.getRequestURI()
        );
        return ResponseEntity.status(409).body(errorDTO);
    }
}