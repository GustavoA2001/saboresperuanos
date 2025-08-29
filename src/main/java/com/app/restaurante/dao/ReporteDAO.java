package com.app.restaurante.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReporteDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReporteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Obtener Ingresos Totales
    public BigDecimal obtenerIngresosTotales() {
        String sql = "SELECT SUM(pg.TotalPago) AS IngresosTotales " +
                     "FROM pedido p " +
                     "INNER JOIN pago pg ON p.IDPedido = pg.IDPedido " +
                     "WHERE p.Estado = 'Pagado'";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }

    // Obtener Ingresos por Producto
    public List<Map<String, Object>> obtenerIngresosPorProducto() {
        String sql = "SELECT p.NomProducto, SUM(p.Cantidad * p.PrecioUnitario) AS IngresosTotales " +
                     "FROM producto p " +
                     "INNER JOIN carrito c ON p.IDProducto = c.IDProducto " +
                     "INNER JOIN pedido pd ON c.IDPedido = pd.IDPedido " +
                     "INNER JOIN pago pg ON pd.IDPedido = pg.IDPedido " +
                     "WHERE pd.Estado = 'Pagado' " +
                     "GROUP BY p.NomProducto";

        return jdbcTemplate.queryForList(sql);
    }

    // Obtener Ingresos por Cliente
    public List<Map<String, Object>> obtenerIngresosPorCliente() {
        String sql = "SELECT cl.Nombre, SUM(pg.TotalPago) AS TotalGastado " +
                     "FROM cliente cl " +
                     "INNER JOIN pedido pd ON cl.IDCliente = pd.IDCliente " +
                     "INNER JOIN pago pg ON pd.IDPedido = pg.IDPedido " +
                     "WHERE pd.Estado = 'Pagado' " +
                     "GROUP BY cl.Nombre " +
                     "ORDER BY TotalGastado DESC";

        return jdbcTemplate.queryForList(sql);
    }
}