package com.app.restaurante.dao;

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

    // === PEDIDOS Y VENTAS POR MES ===
    public List<Map<String, Object>> obtenerPedidosYVentasPorMes() {
        String sql = """
            SELECT 
                DATE_FORMAT(p.FechaPedido, '%Y-%m') AS mes,
                COUNT(DISTINCT p.IDPedido) AS total_pedidos,
                IFNULL(SUM(c.Cantidad * c.PrecioProducto), 0) AS total_ventas
            FROM pedido p
            LEFT JOIN carrito c ON p.IDPedido = c.IDPedido
            GROUP BY DATE_FORMAT(p.FechaPedido, '%Y-%m')
            ORDER BY mes;
        """;
        return jdbcTemplate.queryForList(sql);
    }
}
