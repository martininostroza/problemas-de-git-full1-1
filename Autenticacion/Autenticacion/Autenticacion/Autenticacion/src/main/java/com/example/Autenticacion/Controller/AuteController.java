package com.example.Autenticacion.Controller;

import com.example.Autenticacion.DTO.AuteDTO;
import com.example.Autenticacion.Model.AuteUsuario;
import com.example.Autenticacion.Service.AuteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/autenticacion") 
public class AuteController {

    @Autowired
    private AuteService service;

    // POST: Registrar credenciales de un usuario nuevo
    // (El @Valid activa las reglas del Model. Si el correo viene vacío o mal escrito, se cancela la petición aquí)
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody AuteUsuario usuario) {
        AuteUsuario nuevo = service.registrar(usuario);
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Autenticación correcta: Credenciales guardadas");
        res.put("email", nuevo.getEmail());
        
        // Devuelve un 201 Created confirmando que los datos se guardaron con éxito
        return ResponseEntity.status(201).body(res);
    }

    // GET: Listar todos los usuarios en la base de datos de autenticación
    @GetMapping("/listar")
    public ResponseEntity<List<AuteDTO>> listar() {
        return ResponseEntity.status(200).body(service.listarTodos());
    }

    // PUT: Actualizar la contraseña de un usuario mediante su ID
    @PutMapping("/actualizar-password/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        AuteUsuario actualizado = service.actualizarPassword(id, body.get("password"));
        
        // MANEJO DE ERROR: Si el Service devuelve null significa que el ID no existe en la base de datos (404 Not Found)
        if (actualizado == null) {
            Map<String, Object> errorRes = new HashMap<>();
            errorRes.put("error", "Usuario no encontrado en autenticación");
            return ResponseEntity.status(404).body(errorRes);
        }

        // Si el usuario sí existía, confirma el cambio devolviendo un 200 OK
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Contraseña actualizada y encriptada");
        return ResponseEntity.ok(res);
    }

    // POST: Validar ingreso (Login) al sistema
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        AuteUsuario usuarioValido = service.verificarCredenciales(body.get("email"), body.get("password"));

        // MANEJO DE ERROR: Si las credenciales no coinciden, el Service devuelve null.
        // Arroja un error 401 Unauthorized (No autorizado) avisando que son incorrectas.
        if (usuarioValido == null) {
            Map<String, Object> errorRes = new HashMap<>();
            errorRes.put("error", "Credenciales incorrectas");
            return ResponseEntity.status(401).body(errorRes); 
        }

        // Si el email y contraseña coinciden, aprueba el acceso con un 200 OK y entrega los datos de sesión
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Ingreso exitoso al sistema");
        res.put("email", usuarioValido.getEmail());
        res.put("rol", usuarioValido.getRol());
        return ResponseEntity.status(200).body(res);
    }
}