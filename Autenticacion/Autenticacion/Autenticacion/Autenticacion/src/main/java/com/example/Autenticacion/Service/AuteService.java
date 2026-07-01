package com.example.Autenticacion.Service;

import com.example.Autenticacion.DTO.AuteDTO;
import com.example.Autenticacion.Model.AuteUsuario;
import com.example.Autenticacion.Repository.AuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AuteService {

    @Autowired
    private AuteRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public AuteUsuario registrar(AuteUsuario usuario) {
        // la regla de negocio seria Validar que el email no esté registrado previamente
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado en el sistema");
        }
        
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public List<AuteDTO> listarTodos() {
        return repository.findAll().stream()
                .map(u -> new AuteDTO(u.getId(), u.getEmail(), u.getRol()))
                .collect(Collectors.toList());
    }

    public AuteUsuario actualizarPassword(Integer id, String nuevaPassword) {
        // la regla de negocio seria Lanzar error claro si el usuario no existe
        AuteUsuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con el ID proporcionado: " + id));

        usuario.setPassword(encoder.encode(nuevaPassword));
        return repository.save(usuario);
    }

    public AuteUsuario verificarCredenciales(String email, String passwordFormulario) {
        // la regla de negocio seria Controlar las credenciales inválidas sin retornar null
        AuteUsuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("El correo electrónico o la contraseña son incorrectos"));

        if (!encoder.matches(passwordFormulario, usuario.getPassword())) {
            throw new IllegalArgumentException("El correo electrónico o la contraseña son incorrectos");
        }

        return usuario;
    }
}