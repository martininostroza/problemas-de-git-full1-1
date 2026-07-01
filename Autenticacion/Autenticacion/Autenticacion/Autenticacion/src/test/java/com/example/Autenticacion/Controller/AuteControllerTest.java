package com.example.Autenticacion.Controller;

import com.example.Autenticacion.DTO.AuteDTO;
import com.example.Autenticacion.Model.AuteUsuario;
import com.example.Autenticacion.Service.AuteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuteControllerTest {

    @Test
    void registrar_exito_201() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();

        // Inyectar mock vía reflexión (evita depender de spring-test/webmvc)
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        AuteUsuario saved = new AuteUsuario();
        saved.setId(1);
        saved.setEmail("user@test.com");
        saved.setPassword("encoded-pass");
        saved.setRol("CLIENTE");

        Mockito.when(service.registrar(Mockito.any(AuteUsuario.class))).thenReturn(saved);

        AuteUsuario input = new AuteUsuario();
        input.setEmail("user@test.com");
        input.setPassword("123456");
        input.setRol("CLIENTE");

        var resp = controller.registrar(input);

        assertEquals(201, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp.getBody();
        assertEquals("Autenticación correcta: Credenciales guardadas", body.get("mensaje"));
        assertEquals("user@test.com", body.get("email"));
    }

    @Test
    void listar_exito_200() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        whenListarTodos(service, List.of(new AuteDTO(1, "a@test.com", "CLIENTE")));

        var resp = controller.listar();
        assertEquals(200, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        List<AuteDTO> body = (List<AuteDTO>) resp.getBody();
        assertEquals(1, body.size());
        assertEquals("a@test.com", body.get(0).getEmail());
        assertEquals("CLIENTE", body.get(0).getRol());

    }

    @Test
    void actualizarPassword_id_no_encontrado_404() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        Mockito.when(service.actualizarPassword(Mockito.eq(10), Mockito.anyString())).thenReturn(null);

        @SuppressWarnings("unchecked")
        var resp = controller.actualizar(10, Map.of("password", "newpass123"));

        assertEquals(404, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp.getBody();
        assertEquals("Usuario no encontrado en autenticación", body.get("error"));
    }

    @Test
    void actualizarPassword_exito_200() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        AuteUsuario updated = new AuteUsuario();
        updated.setId(10);
        updated.setEmail("user@test.com");
        updated.setRol("CLIENTE");
        updated.setPassword("encoded-new");

        Mockito.when(service.actualizarPassword(Mockito.eq(10), Mockito.anyString())).thenReturn(updated);

        var resp = controller.actualizar(10, Map.of("password", "newpass123"));
        assertEquals(200, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp.getBody();
        assertEquals("Contraseña actualizada y encriptada", body.get("mensaje"));
    }

    @Test
    void login_credenciales_incorrectas_401() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        Mockito.when(service.verificarCredenciales(Mockito.eq("user@test.com"), Mockito.eq("wrong-pass"))).thenReturn(null);

        var resp = controller.login(Map.of(
                "email", "user@test.com",
                "password", "wrong-pass"
        ));

        assertEquals(401, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp.getBody();
        assertEquals("Credenciales incorrectas", body.get("error"));
    }

    @Test
    void login_exito_200() {
        AuteService service = Mockito.mock(AuteService.class);
        AuteController controller = new AuteController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "service", service);

        AuteUsuario valido = new AuteUsuario();
        valido.setId(1);
        valido.setEmail("user@test.com");
        valido.setRol("CLIENTE");
        valido.setPassword("encoded-pass");

        Mockito.when(service.verificarCredenciales(Mockito.eq("user@test.com"), Mockito.eq("123456"))).thenReturn(valido);

        var resp = controller.login(Map.of(
                "email", "user@test.com",
                "password", "123456"
        ));

        assertEquals(200, resp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp.getBody();
        assertEquals("Ingreso exitoso al sistema", body.get("mensaje"));
        assertEquals("user@test.com", body.get("email"));
        assertEquals("CLIENTE", body.get("rol"));
    }

    private static void whenListarTodos(AuteService service, List<AuteDTO> dtos) {
        Mockito.when(service.listarTodos()).thenReturn(dtos);
    }
}

