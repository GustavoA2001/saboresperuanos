package com.app.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/clientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = clienteDAO.findAll(); // Obtener la lista de clientes
        model.addAttribute("clientes", clientes); // Agregar la lista al modelo
        return "listCliente"; // Nombre de la vista que se mostrará
    }
}