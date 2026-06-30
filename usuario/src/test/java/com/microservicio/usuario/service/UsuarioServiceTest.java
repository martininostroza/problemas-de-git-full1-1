package com.microservicio.usuario.service;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.RolRepository;
import com.microservicio.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;
    private Rol rolAdmin;

    @BeforeEach
    void setUp() {
        rolAdmin = new Rol(1, "ADMIN");
        usuario1 = new Usuario(1, "Juan", "Perez", "juan.perez@test.com", "password123", rolAdmin);
        usuario2 = new Usuario(2, "Ana", "Gomez", "ana.gomez@test.com", "password456", rolAdmin);
    }

    @Test
    void testListarUsuarios() {
        // Given: El repositorio devolverá una lista de dos usuarios
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        // When: Se llama al método para listar usuarios
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Then: La lista no debe ser nula y debe contener 2 usuarios
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll(); // Verifica que el método del repo fue llamado una vez
    }

    @Test
    void testBuscarPorId_cuandoUsuarioExiste() {
        // Given: El repositorio encontrará un usuario con ID 1
        when(usuarioRepository.findByIdUsuario(1)).thenReturn(Optional.of(usuario1));

        // When: Se busca el usuario por ID
        Usuario encontrado = usuarioService.buscarPorId(1);

        // Then: El usuario devuelto debe ser el correcto
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdUsuario());
        assertEquals("Juan", encontrado.getNombre());
        verify(usuarioRepository, times(1)).findByIdUsuario(1);
    }

    @Test
    void testBuscarPorId_cuandoUsuarioNoExiste() {
        // Given: El repositorio no encontrará un usuario con ID 99
        Integer idInexistente = 99;
        when(usuarioRepository.findByIdUsuario(idInexistente)).thenReturn(Optional.empty());

        // When & Then: Al buscar, se debe lanzar una excepción IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.buscarPorId(idInexistente);
        });

        assertEquals("No se encontró ningún usuario con el ID: " + idInexistente, exception.getMessage());
        verify(usuarioRepository, times(1)).findByIdUsuario(idInexistente);
    }

    @Test
    void testAgregarUsuario_Exitoso() {
        // Given: El email del nuevo usuario no existe y el rol sí
        Usuario nuevoUsuario = new Usuario(null, "Carlos", "Lopez", "carlos.lopez@test.com", "pass", rolAdmin);
        when(usuarioRepository.findByEmailIgnoreCase(nuevoUsuario.getEmail())).thenReturn(Optional.empty());
        when(rolRepository.findByIdRol(rolAdmin.getIdRol())).thenReturn(Optional.of(rolAdmin));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        // When: Se agrega el nuevo usuario
        Usuario usuarioGuardado = usuarioService.agregarUsuario(nuevoUsuario);

        // Then: El usuario guardado no debe ser nulo y debe tener los datos correctos
        assertNotNull(usuarioGuardado);
        assertEquals("carlos.lopez@test.com", usuarioGuardado.getEmail());
        verify(usuarioRepository, times(1)).findByEmailIgnoreCase(nuevoUsuario.getEmail());
        verify(rolRepository, times(1)).findByIdRol(rolAdmin.getIdRol());
        verify(usuarioRepository, times(1)).save(nuevoUsuario);
    }

    @Test
    void testEliminarUsuario_cuandoUsuarioNoExiste() {
        // Given: El repositorio indica que el usuario con ID 99 no existe
        Integer idInexistente = 99;
        when(usuarioRepository.existsById(idInexistente)).thenReturn(false);

        // When & Then: Al intentar eliminar, se debe lanzar una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminarUsuario(idInexistente);
        });

        assertEquals("No se puede eliminar: El usuario con ID " + idInexistente + " no existe.", exception.getMessage());

        // Verifica que el método de borrado NUNCA fue llamado
        verify(usuarioRepository, never()).deleteById(idInexistente);
    }
}