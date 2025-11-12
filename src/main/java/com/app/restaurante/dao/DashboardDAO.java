package com.app.restaurante.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardDAO {
    private final JdbcTemplate jdbcTemplate;

    public DashboardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1️ Pedidos y Ventas por Mes
    public List<Map<String, Object>> obtenerPedidosYVentasPorMes() {
        String sql = """
                SELECT DATE_FORMAT(p.FechaPedido, '%Y-%m') AS mes, COUNT(DISTINCT p.IDPedido) AS total_pedidos, IFNULL(SUM(pg.PagoTotal),0) AS total_ventas FROM pedido p INNER JOIN pago pg ON pg.IDPedido = p.IDPedido WHERE p.EstadoPedido = 'Pagado' GROUP BY DATE_FORMAT(p.FechaPedido, '%Y-%m') ORDER BY mes; """;
        return jdbcTemplate.queryForList(sql);
    }

    // 2️ Clientes Activos e Inactivos
    public int obtenerTotalClientes() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cliente", Integer.class);
    }

    public int obtenerClientesActivosMesActual() {
        LocalDate ahora = LocalDate.now();
        int mes = ahora.getMonthValue();
        int año = ahora.getYear();

        String sql = """
                SELECT COUNT(DISTINCT c.IDCliente)
                FROM cliente c
                INNER JOIN pedido p ON p.IDCliente = c.IDCliente
                WHERE p.EstadoPedido = 'Pagado'
                  AND MONTH(p.FechaPedido) = ?
                  AND YEAR(p.FechaPedido) = ?;
                """;

        return jdbcTemplate.queryForObject(sql, Integer.class, mes, año);
    }

    // 3️ Porcentaje de Ventas por Producto
    public List<Map<String, Object>> obtenerPorcentajeVentasPorProductoMesActual() {
        LocalDate ahora = LocalDate.now();
        int mes = ahora.getMonthValue();
        int año = ahora.getYear();

        String sql = """
                SELECT ph.NomProducto AS producto,
                ROUND((SUM(c.Cantidad*c.PrecioProducto) /
                      (SELECT SUM(c2.Cantidad*c2.PrecioProducto)
                       FROM carrito c2
                       INNER JOIN pedido p2 ON c2.IDPedido=p2.IDPedido
                       WHERE p2.EstadoPedido='Pagado' AND MONTH(p2.FechaPedido)=? AND YEAR(p2.FechaPedido)=?))*100,2) AS porcentaje
                FROM carrito c
                INNER JOIN pedido p ON c.IDPedido=p.IDPedido
                INNER JOIN producto pr ON c.IDProducto=pr.IDProducto
                INNER JOIN productohistoria ph ON pr.IDProdHistoria=ph.IDProdHistoria
                WHERE p.EstadoPedido='Pagado' AND MONTH(p.FechaPedido)=? AND YEAR(p.FechaPedido)=?
                GROUP BY ph.IDProdHistoria, ph.NomProducto
                ORDER BY porcentaje DESC;
                """;
        return jdbcTemplate.queryForList(sql, mes, año, mes, año);
    }

    // 4️ Ventas Totales por Producto (mostrar todos, incluso sin ventas)
    public List<Map<String, Object>> obtenerVentasTotalesPorProductoMesActual() {
        LocalDate ahora = LocalDate.now();
        int mes = ahora.getMonthValue();
        int año = ahora.getYear();

        String sql = """
                SELECT ph.NomProducto AS producto,
                       IFNULL(ROUND(SUM(CASE WHEN MONTH(p.FechaPedido)=? AND YEAR(p.FechaPedido)=? THEN c.Cantidad*c.PrecioProducto ELSE 0 END),2),0) AS total_venta
                FROM productohistoria ph
                INNER JOIN producto pr ON ph.IDProdHistoria = pr.IDProdHistoria
                LEFT JOIN carrito c ON c.IDProducto = pr.IDProducto
                LEFT JOIN pedido p ON c.IDPedido = p.IDPedido AND p.EstadoPedido = 'Pagado'
                GROUP BY ph.IDProdHistoria, ph.NomProducto
                ORDER BY total_venta DESC;
                """;
        return jdbcTemplate.queryForList(sql, mes, año);
    }
}