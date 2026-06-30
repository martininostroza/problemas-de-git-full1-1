package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import com.microservicio.usuario.dto.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controlador de Usuarios", description = "Endpoints principales para el registro, consulta, modificación y eliminación de perfiles de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // GET: Listar todos los usuarios
    @Operation(
        summary = "Listar todos los usuarios",
        description = "Devuelve una lista sin filtros con todas las cuentas de usuario almacenadas en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.status(200).body(service.listarUsuarios());
    }

    // GET: Buscar usuario por su ID
    @Operation(
        summary = "Buscar usuario por ID",
        description = "Obtiene los datos completos de una cuenta mediante su identificador numérico único. Sincronizado para interacciones remotas con RestTemplate."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario localizado y devuelto con éxito."),
        @ApiResponse(responseCode = "404", description = "El ID proporcionado no corresponde a ningún usuario registrado."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/buscar-id/{idUsuario}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer idUsuario) {
        Usuario usuario = service.buscarPorId(idUsuario);
        return ResponseEntity.status(200).body(usuario);
    }

    // GET: Buscar usuario por su correo
    @Operation(
        summary = "Buscar usuario por Email",
        description = "Busca y devuelve la información de un usuario utilizando su dirección de correo electrónico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta asociada al email encontrada exitosamente."),
        @ApiResponse(responseCode = "404", description = "No existe ningún usuario registrado con el correo provisto."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = service.buscarPorEmail(email);
        return ResponseEntity.status(200).body(usuario);
    }

    // POST: Guardar un usuario nuevo
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea una cuenta en la base de datos aplicando las validaciones estructurales del modelo y las reglas lógicas del Service."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado y guardado con éxito."),
        @ApiResponse(responseCode = "400", description = "Los parámetros del JSON de entrada no superan las validaciones requeridas."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PostMapping("/agregar")
    public ResponseEntity<Usuario> agregarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = service.agregarUsuario(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
    
    // PUT: Modificar datos de un usuario
    @Operation(
        summary = "Actualizar datos de un usuario por ID",
        description = "Modifica los atributos del usuario especificado por su ID en base al cuerpo JSON enviado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la cuenta actualizados de manera exitosa."),
        @ApiResponse(responseCode = "400", description = "El JSON de actualización contiene propiedades o datos inválidos."),
        @ApiResponse(responseCode = "404", description = "No se localizó al usuario con el ID provisto para modificarlo."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer idUsuario, @Valid @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = service.actualizarUsuario(idUsuario, usuarioActualizado);
        return ResponseEntity.status(200).body(usuario);
    }

    // DELETE: Borrar un usuario
    @Operation(
        summary = "Eliminar un usuario del sistema",
        description = "Remueve de forma permanente un registro de usuario utilizando su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El usuario se ha eliminado correctamente de la base de datos."),
        @ApiResponse(responseCode = "404", description = "El identificador enviado no coincide con ningún registro activo."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer idUsuario) {
        service.eliminarUsuario(idUsuario);
        return ResponseEntity.status(200).body("Usuario eliminado exitosamente.");
    }

    // GET: Obtener la vista DTO resumida
    @Operation(
        summary = "Obtener detalle simple de usuario (DTO)",
        description = "Genera una vista optimizada y reducida (Data Transfer Object) con los campos esenciales del usuario para comunicación ágil."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "DTO construido y enviado con éxito."),
        @ApiResponse(responseCode = "404", description = "No se pudo generar el DTO porque el ID del usuario no existe."),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor.")
    })
    @GetMapping("/{idUsuario}/detalle-simple")
    public ResponseEntity<UsuarioDTO> obtenerDetalleSimple(@PathVariable Integer idUsuario) {
        UsuarioDTO dto = service.obtenerDetalleSimple(idUsuario);
        return ResponseEntity.status(200).body(dto);
    }
}