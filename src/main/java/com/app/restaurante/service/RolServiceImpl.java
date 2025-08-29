package com.app.restaurante.service;

import com.app.restaurante.model.Rol;
import com.app.restaurante.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listarRol() {
        return rolRepository.findAll();
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol obtenerRolPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol actualizarRol(Rol rol){
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }
}
