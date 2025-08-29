package com.app.restaurante.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.dao.DireccionDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Direccion;

import jakarta.servlet.http.HttpSession;

@Controller
public class DireccionController {

    @Autowired
    private DireccionDAO direccionDAO;


    @Autowired
    private HttpSession session;

    @PostMapping("/guardarDireccion")
    public String guardarDireccion(@RequestParam("direccion") String direccion,                                   
                                   @RequestParam("distrito") Long distritoId,
                                   @RequestParam("referencia") String referencia,
                                   RedirectAttributes redirectAttributes,
                                   @SessionAttribute("cliente") Cliente cliente) {
        // Verificar que el cliente este presente en la sesión
        if (cliente == null) {
            return "redirect:/login"; 
        }
        
        // Obtener el idCliente de la sesion
        Long idCliente = (Long) session.getAttribute("idCliente");

        Direccion direccioncli = new Direccion();
        direccioncli.setDireccion(direccion);
        direccioncli.setIdDistrito(distritoId);
        direccioncli.setReferencia(referencia);
        direccioncli.setIdCliente(idCliente);
        
        direccionDAO.saveDireccion(direccioncli);

        return "redirect:/bienvenido";
        //redirectAttributes.addFlashAttribute("success", "Dirección registrada exitosamente");
        //return "redirect:/registro_completar"; // Redirigir a una página de éxito o continuar el proceso
    }

    @GetMapping("/registro_direccion")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

}
