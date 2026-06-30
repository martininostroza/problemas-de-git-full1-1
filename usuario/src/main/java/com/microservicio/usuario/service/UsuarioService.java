package com.microservicio.usuario.service;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.RolRepository;
import com.microservicio.usuario.repository.UsuarioRepository;
import com.microservicio.usuario.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // EXCEPCIÓN DIRECTA: Desempaquetamos el Optional de inmediato
    public Usuario buscarPorId(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con el ID: " + idUsuario));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con el correo electrónico: " + email));
    }

    public Usuario agregarUsuario(Usuario usuario) {
        // Evita que se registren emails duplicados
        if (usuarioRepository.findByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado por otro usuario.");
        }

        if (usuario.getRol() != null && usuario.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findByIdRol(usuario.getRol().getIdRol())
                    .orElseThrow(() -> new IllegalArgumentException("El rol especificado con ID " + usuario.getRol().getIdRol() + " no existe."));
            usuario.setRol(rol);
        }
        return usuarioRepository.save(usuario);
    }

    // CONTROL TOTAL DESDE SERVICE: Devuelve el objeto modificado o rompe el flujo con excepción
    public Usuario actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar: El usuario con ID " + idUsuario + " no existe."));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        
        if (usuarioActualizado.getRol() != null && usuarioActualizado.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findByIdRol(usuarioActualizado.getRol().getIdRol())
                    .orElseThrow(() -> new IllegalArgumentException("El rol especificado con ID " + usuarioActualizado.getRol().getIdRol() + " no existe."));
            usuario.setRol(rol);
        }

        return usuarioRepository.save(usuario);
    }

    // Borrado validando previamente la existencia del registro
    public void eliminarUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("No se puede eliminar: El usuario con ID " + idUsuario + " no existe.");
        }
        usuarioRepository.deleteById(idUsuario);
    }

    public UsuarioDTO obtenerDetalleSimple(Integer idUsuario) {
        Usuario u = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el detalle del usuario con ID: " + idUsuario));

        String nombreRol = (u.getRol() != null) ? u.getRol().getNombreRol() : "SIN_ROL";

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombre(u.getNombre());
        dto.setEmail(u.getEmail());
        dto.setNombreRol(nombreRol);
        
        return dto;
    }
}