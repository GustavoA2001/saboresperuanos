package com.app.restaurante.controller;

import com.app.restaurante.dao.EmpleadosDAO;
import com.app.restaurante.model.Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/empleados")
public class EmpleadosController {

    @Autowired
    private EmpleadosDAO empleadosDAO;

    // Modal NUEVO empleado
    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model) {
        model.addAttribute("empleado", new Empleados());
        model.addAttribute("modo", "nuevo");
        return "admin/components/admin_form_empleado :: modalContenido";
    }

    // Modal EDITAR empleado
    @GetMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable int id, Model model) {
        Empleados empleado = empleadosDAO.obtenerEmpleadoPorId(id);
        model.addAttribute("empleado", empleado);
        model.addAttribute("modo", "editar");
        return "admin/components/admin_form_empleado :: modalContenido";
    }

    // Modal VER empleado
    @GetMapping("/{id}")
    public String verEmpleado(@PathVariable int id, Model model) {
        Empleados empleado = empleadosDAO.obtenerEmpleadoPorId(id);
        model.addAttribute("empleado", empleado);
        model.addAttribute("modo", "ver");
        return "admin/components/admin_form_empleado :: modalContenido";
    }

    // Guardar (nuevo o editar)
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleados empleado) {
        if (empleado.getIdEmpleado() == 0) {
            empleadosDAO.insertarEmpleado(empleado);
        } else {
            empleadosDAO.actualizarEmpleado(empleado);
        }
        return "redirect:/admin/empleados";
    }

    // Eliminar
    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable int id) {
        empleadosDAO.eliminarEmpleado(id);
        return "redirect:/admin/empleados";
    }
}
