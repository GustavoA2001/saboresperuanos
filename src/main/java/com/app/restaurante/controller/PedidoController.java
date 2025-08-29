package com.app.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.app.restaurante.dao.PedidoDAO;
import com.app.restaurante.model.Pedido;

@Controller
public class PedidoController {

    @Autowired
    private PedidoDAO pedidoDao;

    @GetMapping("/pedidos")
    public String mostrarReportes(Model model) {
        List<Pedido> pedidos = pedidoDao.obtenerPedidosPagados();
        model.addAttribute("pedidos", pedidos);
        return "listPedidos";  // nombre de la vista Thymeleaf
    }
}
