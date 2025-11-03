package com.app.restaurante.controller;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    private final ClienteDAO clienteDAO;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteController(ClienteDAO clienteDAO, JdbcTemplate jdbcTemplate) {
        this.clienteDAO = clienteDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mostrar formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String nuevoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/form_cliente";
    }

    // Ver un cliente específico
    @GetMapping("/{id}")
    public String verCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteDAO.findById(id);
        model.addAttribute("cliente", cliente);
        return "admin/ver_cliente";
    }

    // Eliminar un cliente
    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        jdbcTemplate.update(sql, id);
        return "redirect:/admin/clientes";
    }
}
