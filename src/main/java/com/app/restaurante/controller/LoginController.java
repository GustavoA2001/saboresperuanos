package com.app.restaurante.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import com.app.restaurante.model.Cliente;
import com.app.restaurante.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpSession session;    
    
    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("usuario") String usuario, 
            @RequestParam("contrasena") String password,
            RedirectAttributes redirectAttributes)
            throws NoSuchAlgorithmException, IOException, CloneNotSupportedException {
        
        Cliente cliente = loginService.validateUser(usuario, password);
        
        if (cliente != null) {
            session.setAttribute("idCliente", cliente.getIdCliente());
            session.setAttribute("usuario", cliente.getUsuario());
            session.setAttribute("nombre", cliente.getNombre());
            System.out.println("✅ Usuario logueado: " + cliente.getUsuario());
            return new ModelAndView("redirect:/inicio");
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña inválidos");
            return new ModelAndView("redirect:/login");
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        
        return "redirect:/";
    }

    @GetMapping("/inicio")
public String mostrarInicio() {
    return "index";
}

}
