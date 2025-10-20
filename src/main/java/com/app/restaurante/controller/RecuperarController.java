package com.app.restaurante.controller;

import com.app.restaurante.service.RecuperarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recuperar")
public class RecuperarController {

    @Autowired
    private RecuperarService recuperarService;

    @GetMapping
    public String mostrarPaso1(Model model) {
        model.addAttribute("step", 1);
        return "recuperar";
    }

    @PostMapping("/enviarCorreo")
    public String enviarCorreo(@RequestParam String correo, Model model, HttpSession session) {
        boolean enviado = recuperarService.enviarCodigo(correo);

        if (!enviado) {
            model.addAttribute("mensaje", "error: El correo no está registrado o falló el envío.");
            model.addAttribute("step", 1);
            return "recuperar";
        }

        session.setAttribute("correoRecuperacion", correo);
        model.addAttribute("mensaje", "Código de verificación enviado al correo.");
        model.addAttribute("step", 2);
        return "recuperar";
    }

    @PostMapping("/verificarCodigo")
    public String verificarCodigo(@RequestParam String codigo, Model model, HttpSession session) {
        String correo = (String) session.getAttribute("correoRecuperacion");
        if (correo == null) {
            model.addAttribute("mensaje", "error: Sesión expirada, vuelve a ingresar el correo.");
            model.addAttribute("step", 1);
            return "recuperar";
        }

        boolean verificado = recuperarService.verificarCodigo(correo, codigo);
        if (!verificado) {
            model.addAttribute("mensaje", "error: Código incorrecto o expirado.");
            model.addAttribute("step", 2);
            return "recuperar";
        }

        model.addAttribute("mensaje", "Código verificado correctamente.");
        model.addAttribute("step", 3);
        return "recuperar";
    }

    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena(@RequestParam String nuevaContrasena, Model model, HttpSession session) {
        String correo = (String) session.getAttribute("correoRecuperacion");
        if (correo == null) {
            model.addAttribute("mensaje", "error: Sesión expirada, vuelve a empezar el proceso.");
            model.addAttribute("step", 1);
            return "recuperar";
        }

        boolean cambiado = recuperarService.cambiarContrasena(correo, nuevaContrasena);
        if (!cambiado) {
            model.addAttribute("mensaje", "error: No se pudo actualizar la contraseña.");
            model.addAttribute("step", 3);
            return "recuperar";
        }

        session.invalidate();
        
        return "redirect:/login";
    }
}
