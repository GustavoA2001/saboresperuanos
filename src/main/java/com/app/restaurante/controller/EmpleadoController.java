package com.app.restaurante.controller;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.model.DetalleEmpleado;
import com.app.restaurante.service.EmpleadoService;
import com.app.restaurante.service.RolService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

        @Autowired
    private HttpSession session;


    @GetMapping
    public String listarEmpleados(Model model) {

        List<Empleado> empleados = empleadoService.listarEmpleados();
        model.addAttribute("empleados", empleados);
        return "listEmpleados";
    }

    @GetMapping("/nuevo")
    public String mostrarFormEmpleado(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("detalleEmpleado", new DetalleEmpleado());
        model.addAttribute("roles", rolService.listarRol());
        return "formEmpleados";
    }

    @PostMapping
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) 
        throws IOException, NoSuchAlgorithmException {
        Integer rolId = empleado.getRol().getIdRol();
        empleadoService.guardarEmpleado(empleado, rolId);
        return "redirect:/detalle-empleados/nuevo";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEmpleadoEditar(@PathVariable Integer id, Model model) {

        
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        model.addAttribute("empleado", empleado);
        model.addAttribute("roles", rolService.listarRol());
        return "formEmpleadosEditar";
    }

    @PostMapping("/{id}")
    public String actualizarEmpleado(@PathVariable Integer id, @ModelAttribute Empleado empleado)
    throws IOException, NoSuchAlgorithmException {
        Integer rolId = empleado.getRol().getIdRol();
        empleado.setIdEmpleado(id);
        empleadoService.guardarEmpleado(empleado, rolId);
        return "redirect:/empleados";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarEmpleado(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/empleados";
    }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleado(@PathVariable Integer id) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        Empleado empleadoSinContrasena = new Empleado(
            empleado.getIdEmpleado(),
            empleado.getNombre(),
            empleado.getApellidoPaterno(),
            empleado.getApellidoMaterno(),
            empleado.getUsuario(),
            null,
            empleado.getRol()
        );
        return empleadoSinContrasena;
    }
}

