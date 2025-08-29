package com.app.restaurante.controller;

import com.app.restaurante.model.Rol;
import com.app.restaurante.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        List<Rol> roles = rolService.listarRol();
        model.addAttribute("roles", roles);
        return "listRol";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("rol", new Rol());
        return "formRol";
    }

    @PostMapping
    public String crearRol(@ModelAttribute Rol rol) {
        rolService.guardarRol(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {  
        Rol rol = rolService.obtenerRolPorId(id);
        model.addAttribute("rol", rol);
        return "formRolEditar";
    }

     @PostMapping("/{id}")
    public String actualizarRol(@PathVariable Integer id, @ModelAttribute Rol rol, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rol", rol);
            return "formRolEditar";
        }

        Rol rolExistente = rolService.obtenerRolPorId(id);
        if (rolExistente == null) {
            return "redirect:/roles?error=Rol no encontrado";
        }
        rolExistente.setNomRol(rol.getNomRol());
        rolExistente.setDescripcion(rol.getDescripcion());
        rolService.actualizarRol(rolExistente);

        return "redirect:/roles?mensaje=Rol actualizado exitosamente";
    }


    @GetMapping("/{id}/eliminar")
    public String eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return "redirect:/roles";
    }
}
