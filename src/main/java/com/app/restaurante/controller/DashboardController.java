package com.app.restaurante.controller;

import com.app.restaurante.dao.DashboardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private DashboardDAO dashboardDAO;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        var data = dashboardDAO.obtenerPedidosYVentasPorMes();

        List<String> meses = data.stream()
                .map(row -> row.get("mes").toString())
                .toList();

        List<Integer> pedidos = data.stream()
                .map(row -> Integer.parseInt(row.get("total_pedidos").toString()))
                .toList();

        List<Double> ventas = data.stream()
                .map(row -> Double.parseDouble(row.get("total_ventas").toString()))
                .toList();

        model.addAttribute("meses", meses);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("ventas", ventas);

        model.addAttribute("activeSection", "dashboard");
        return "admin/admin_dashboard";
    }
}
