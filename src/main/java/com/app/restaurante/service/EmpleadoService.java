package com.app.restaurante.service;

import com.app.restaurante.model.Empleado;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

public interface EmpleadoService {
    public List<Empleado> listarEmpleados();

    public Empleado guardarEmpleado(Empleado empleado, @RequestParam Integer rolId) throws IOException, NoSuchAlgorithmException;

    public Empleado obtenerEmpleadoPorId(Integer id);

    public Empleado actualizarEmpleado(Empleado empleado, @RequestParam Integer rolId) throws IOException, NoSuchAlgorithmException;
    
    public void eliminarEmpleado(Integer id);

    // Empleado empleado = empleadoService.validateUser(usuario, password); 
    public Empleado validateUser(String usuario, String contrasena) throws IOException, NoSuchAlgorithmException;

}
