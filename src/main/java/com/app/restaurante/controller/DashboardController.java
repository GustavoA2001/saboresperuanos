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
                
                // === Pedidos y Ventas por Mes ===
                var data = dashboardDAO.obtenerPedidosYVentasPorMes();

                List<String> meses = data.stream().map(r -> r.get("mes").toString()).toList();
                List<Integer> pedidos = data.stream().map(r -> Integer.parseInt(r.get("total_pedidos").toString()))
                                .toList();
                List<Double> ventas = data.stream().map(r -> Double.parseDouble(r.get("total_ventas").toString()))
                                .toList();

                // === Clientes activos/inactivos ===
                int clientesActivos = dashboardDAO.obtenerClientesActivos();
                int totalClientes = dashboardDAO.obtenerTotalClientes();
                int clientesInactivos = totalClientes - clientesActivos;

                // === Porcentaje de ventas por producto ===
                var productosData = dashboardDAO.obtenerPorcentajeVentasPorProducto();
                List<String> productos = productosData.stream().map(r -> r.get("producto").toString()).toList();
                List<Double> porcentajeVentas = productosData.stream()
                                .map(r -> Double.parseDouble(r.get("porcentaje").toString())).toList();

                // === Ventas totales por producto (TODOS) ===
                var ventasPorProducto = dashboardDAO.obtenerVentasTotalesPorProducto();
                List<String> productosTotales = ventasPorProducto.stream().map(r -> r.get("producto").toString())
                                .toList();
                List<Double> totalVentasProducto = ventasPorProducto.stream()
                                .map(r -> Double.parseDouble(r.get("total_venta").toString())).toList();

                model.addAttribute("meses", meses);
                model.addAttribute("pedidos", pedidos);
                model.addAttribute("ventas", ventas);
                model.addAttribute("clientesActivos", clientesActivos);
                model.addAttribute("clientesInactivos", clientesInactivos);
                model.addAttribute("productos", productos);
                model.addAttribute("porcentajeVentas", porcentajeVentas);
                model.addAttribute("productosTotales", productosTotales);
                model.addAttribute("totalVentasProducto", totalVentasProducto);
                model.addAttribute("activeSection", "dashboard");
                return "admin/admin_dashboard";
        }
}