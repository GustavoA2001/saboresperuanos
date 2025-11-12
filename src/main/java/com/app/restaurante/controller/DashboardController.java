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
                int clientesActivosMes = dashboardDAO.obtenerClientesActivosMesActual();
                int totalClientes = dashboardDAO.obtenerTotalClientes();
                int clientesInactivosMes = totalClientes - clientesActivosMes;

                // === Porcentaje de ventas por producto ===
                var productosDataMes = dashboardDAO.obtenerPorcentajeVentasPorProductoMesActual();
                List<String> productosMes = productosDataMes.stream().map(r -> r.get("producto").toString()).toList();
                List<Double> porcentajeVentasMes = productosDataMes.stream()
                                .map(r -> Double.parseDouble(r.get("porcentaje").toString())).toList();

                // === Ventas totales por producto (TODOS) ===
                var ventasPorProductoMes = dashboardDAO.obtenerVentasTotalesPorProductoMesActual();
                List<String> productosTotalesMes = ventasPorProductoMes.stream().map(r -> r.get("producto").toString())
                                .toList();
                List<Double> totalVentasProductoMes = ventasPorProductoMes.stream()
                                .map(r -> Double.parseDouble(r.get("total_venta").toString())).toList();

                model.addAttribute("meses", meses);
                model.addAttribute("pedidos", pedidos);
                model.addAttribute("ventas", ventas);
                model.addAttribute("clientesActivosMes", clientesActivosMes);
                model.addAttribute("clientesInactivosMes", clientesInactivosMes);
                model.addAttribute("totalClientes", totalClientes);

                model.addAttribute("productosMes", productosMes);
                model.addAttribute("porcentajeVentasMes", porcentajeVentasMes);
                model.addAttribute("productosTotalesMes", productosTotalesMes);
                model.addAttribute("totalVentasProductoMes", totalVentasProductoMes);
                model.addAttribute("activeSection", "dashboard");
                return "admin/admin_dashboard";
        }
}