package com.app.restaurante.controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.service.EmpleadoService;

@Controller
public class EmpleadoLoginController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HttpSession session;

    @GetMapping("/loginEmpleado")
    public String mostrarLogin() {
        return "login"; // login.html
    }

    @PostMapping("/loginEmpleado")
    public ModelAndView loginEmpleado(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String password,
            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, IOException {

        Empleado empleado = empleadoService.validateUser(usuario, password);

        if (empleado != null) {
            session.setAttribute("idEmpleado", empleado.getIdEmpleado());
            session.setAttribute("usuario", empleado.getUsuario());
            session.setAttribute("nombre", empleado.getNombre());
            session.setAttribute("rol", empleado.getRol().getIdRol());

            // Redirección según rol
            if (empleado.getRol().getIdRol() == 1) {
                // Admin → panel principal
                return new ModelAndView("redirect:/admin");
            } else if (empleado.getRol().getIdRol() == 4) {
                // Delivery → vista de delivery
                return new ModelAndView("redirect:/delivery");
            } else {
                redirectAttributes.addFlashAttribute("error", "Acceso no permitido");
                return new ModelAndView("redirect:/loginEmpleado");
            }

        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña inválidos");
            return new ModelAndView("redirect:/loginEmpleado");
        }
    }
}
