package com.app.restaurante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        // Verificar si hay sesión activa
        Object usuario = session.getAttribute("usuario");
        Object nombre = session.getAttribute("nombre");
        Object idCliente = session.getAttribute("idCliente");

        // Si hay sesión, pasamos los datos al modelo para personalizar el index
        if (usuario != null && nombre != null && idCliente != null) {
            model.addAttribute("nombre", nombre);
            model.addAttribute("usuario", usuario);
            model.addAttribute("idCliente", idCliente);
        }

        // Retorna la vista index.html (desde templates)
        return "index";
    }
}
