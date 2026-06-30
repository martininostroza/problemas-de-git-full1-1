package com.microservicio.usuario.service;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository repository;

    public List<Rol> listarRoles() {
        return repository.findAll();
    }

    public Optional<Rol> buscarPorIdRol(Integer idRol) {
        return repository.findByIdRol(idRol);
    }

    public Optional<Rol> buscarPorNombreRol(String nombreRol) {
        return repository.findByNombreRol(nombreRol);
    }

    
    public Rol agregarRol(Rol rol) {
        Optional<Rol> existente = repository.findByNombreRol(rol.getNombreRol());
        if (existente.isPresent()) {
            return existente.get(); // Si ya existe el rol ADMIN, te devuelve el que ya está y no rompe nada
        }
        return repository.save(rol);
    }

    public Rol actualizarRol(Integer idRol, Rol rolActualizado) {
        Optional<Rol> existe = repository.findByIdRol(idRol);
        if (existe.isEmpty()) {
            return null;
        }
        Rol rol = existe.get();
        rol.setNombreRol(rolActualizado.getNombreRol());
        return repository.save(rol);
    }
}