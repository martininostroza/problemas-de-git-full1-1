package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.service.RolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@Tag(name = "Controlador de Roles", description = "Endpoints para la gestión y asignación de roles y permisos de usuarios en el sistema")
public class RolController {

    @Autowired
    private RolService service;

    // GET: Listar todos los roles
    @Operation(
        summary = "Listar todos los roles",
        description = "Devuelve una lista completa con todos los roles registrados en el sistema del microservicio."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de roles cargada con éxito."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/listarRol")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.status(200).body(service.listarRoles());
    }
    
    // GET: Buscar por ID de rol
    @Operation(
        summary = "Buscar rol por ID",
        description = "Obtiene los detalles de un rol específico utilizando su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado y devuelto correctamente."),
        @ApiResponse(responseCode = "404", description = "El ID del rol proporcionado no existe en el sistema."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<Rol> buscarPorIdRol(@PathVariable Integer idRol) {
        Optional<Rol> rolOpt = service.buscarPorIdRol(idRol);
        
        if (rolOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        
        return ResponseEntity.status(200).body(rolOpt.get());
    }

    // POST: Agregar un nuevo rol
    @Operation(
        summary = "Agregar un nuevo rol",
        description = "Registra un nuevo rol (ej. ADMIN, USER) validando que el cuerpo de la petición contenga los datos correctos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rol creado de manera exitosa."),
        @ApiResponse(responseCode = "400", description = "El cuerpo JSON enviado no supera las validaciones requeridas."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/agregarRol")
    public ResponseEntity<Rol> agregarRol(@Valid @RequestBody Rol rol) {
        Rol nuevoRol = service.agregarRol(rol);
        return ResponseEntity.status(201).body(nuevoRol);
    }

    // PUT: Actualizar un rol existente
    @Operation(
        summary = "Actualizar un rol existente",
        description = "Modifica las propiedades de un rol en base a su ID único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol modificado y actualizado de manera exitosa."),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún rol coincidente con el ID provisto."),
        @ApiResponse(responseCode = "400", description = "El JSON de actualización contiene datos inválidos."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PutMapping("/actualizarRol/{idRol}")
    public ResponseEntity<String> actualizarRol(@PathVariable Integer idRol, @Valid @RequestBody Rol rolActualizado) {
        Optional<Rol> rolExiste = service.buscarPorIdRol(idRol);
        
        if (rolExiste.isEmpty()) {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
        
        service.actualizarRol(idRol, rolActualizado);
        return ResponseEntity.status(200).body("Rol actualizado exitosamente");
    }
}