package com.app.restaurante.service;

import java.util.List;
import com.app.restaurante.model.Rol;

public interface RolService {
    public List<Rol> listarRol();

    public Rol guardarRol(Rol rol);

    public Rol obtenerRolPorId(Integer id);
    public Rol actualizarRol(Rol rol);
    
    public void eliminarRol(Integer id);
}
