package com.app.restaurante.service;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.model.Rol;
import com.app.restaurante.repository.EmpleadoRepository;
import com.app.restaurante.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolService rolService;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado, @RequestParam Integer rolId) throws IOException, NoSuchAlgorithmException {
        // Hashear la contraseña antes de guardar
        String hashedPassword = Validation.md5(empleado.getContrasena());
        empleado.setContrasena(hashedPassword);

        Rol rol = rolService.obtenerRolPorId(rolId);
        empleado.setRol(rol);
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado, @RequestParam Integer rolId) throws IOException, NoSuchAlgorithmException {
        // Hashear la contraseña antes de actualizar
        String hashedPassword = Validation.md5(empleado.getContrasena());
        empleado.setContrasena(hashedPassword);
        
        Rol rol = rolService.obtenerRolPorId(rolId);
        empleado.setRol(rol);
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminarEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    public Empleado validateUser(String usuario, String contrasena) throws NoSuchAlgorithmException, IOException {
        // Hashear la contraseña proporcionada para la validación
        String hashedPassword = Validation.md5(contrasena);
        System.out.println("Intentando autenticar usuario: " + usuario);
        System.out.println("Contraseña hasheada: " + hashedPassword);

        Empleado empleado = empleadoRepository.findByUsuarioAndContrasena(usuario, hashedPassword);

        if (empleado == null) {
            System.out.println("No se encontró el empleado");
        } else {
            System.out.println("Empleado encontrado: " + empleado.getNombre());
            System.out.println("Rol del empleado: " + empleado.getRol().getIdRol());
        }

        return empleado;
    }
}