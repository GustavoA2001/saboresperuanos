package com.app.restaurante.controller;

import com.app.restaurante.model.DetalleEmpleado;
import com.app.restaurante.service.DetEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@Controller
@RequestMapping("/detalle-empleados")
public class DetalleEmpleadoController {

    @Autowired
    private DetEmpleadoService detEmpleadoService;

    @GetMapping
    public String listarDetEmpleados(Model model) {
        List<DetalleEmpleado> detalles = detEmpleadoService.listarDetEmpleado();
        model.addAttribute("detalles", detalles);
        return "listDetEmpleados"; // Asegúrate de tener una vista para listar detalles
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model model) {
        model.addAttribute("detalleEmpleado", new DetalleEmpleado());
        
        
        // Aquí puedes agregar la lista de empleados para el selector
        return "formDetEmpleados"; // Vista para crear un nuevo detalle empleado
    }

    @PostMapping
    public String guardarDetEmpleado(@RequestPart MultipartFile file, @ModelAttribute DetalleEmpleado detalleEmpleado) throws IOException {
        detEmpleadoService.guardarDetEmpleado(detalleEmpleado, file);
        return "redirect:/empleados";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Integer id, Model model) {
        DetalleEmpleado detalleEmpleado = detEmpleadoService.obtenerDetEmpleadoPorId(id);
        model.addAttribute("detalleEmpleado", detalleEmpleado);
        // Aquí puedes agregar la lista de empleados para el selector
        return "formDetEmpleados"; // Vista para editar el detalle empleado
    }

    @PostMapping("/{id}")
    public String actualizarDetEmpleado(@PathVariable Integer id, @RequestPart MultipartFile file, @ModelAttribute DetalleEmpleado detalleEmpleado) throws IOException {
        detalleEmpleado.setIdDetalleEmp(id);
        detEmpleadoService.actualizarDetEmpleado(detalleEmpleado, file);
        return "redirect:/empleados";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarDetEmpleado(@PathVariable Integer id) throws IOException {
        detEmpleadoService.eliminarDetEmpleado(id);
        return "redirect:/detalle-empleados";
    }
}
