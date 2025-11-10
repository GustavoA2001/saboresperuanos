package com.app.restaurante.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.restaurante.dao.CheckoutDAO;

@Controller
public class ReciboController {

    @Autowired
    private CheckoutDAO checkoutDAO;

    @GetMapping("/recibo/{idPedido}")
    public String verRecibo(@PathVariable int idPedido, Model model) {
        System.out.println("[ReciboController] Cargando recibo para pedido #" + idPedido);

        try {
            List<Map<String, Object>> datos = checkoutDAO.obtenerDatosPedido(idPedido);
            if (datos.isEmpty()) {
                System.out.println("[ReciboController] No se encontró el pedido #" + idPedido);
                model.addAttribute("error", "No se encontró información del pedido #" + idPedido);
                return "error";
            }

            Map<String, Object> pedido = datos.get(0);
            System.out.println("[ReciboController] Datos del pedido: " + pedido);

            List<Map<String, Object>> productos = checkoutDAO.obtenerProductosPedido(idPedido);
            System.out.println("[ReciboController] Productos obtenidos: " + productos.size());

            model.addAttribute("pedido", pedido);
            model.addAttribute("productos", productos);

            System.out.println("[ReciboController] Datos enviados a la vista recibo.html");
            return "recibo";

        } catch (Exception e) {
            System.err.println("[ReciboController] Error al generar recibo: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error interno al generar el recibo");
            return "error";
        }
    }

    // Permitir /recibo?idPedido=...
    @GetMapping("/recibo")
    public String verReciboParam(@RequestParam int idPedido, Model model) {
        System.out.println("📥 [ReciboController] Recibo solicitado por parámetro idPedido=" + idPedido);
        return verRecibo(idPedido, model);
    }
}