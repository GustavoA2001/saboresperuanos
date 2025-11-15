package com.app.restaurante.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ReporteDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReporteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==========================================================
    // 1. REPORTE: VENTAS TOTALES
    // ==========================================================
    public Double obtenerVentasTotales(Date inicio, Date fin) {
        String sql = """
                    SELECT IFNULL(SUM(p.MontoFinal), 0)
                    FROM pedido p
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                """;

        return jdbcTemplate.queryForObject(sql, Double.class, inicio, fin);
    }

    // ==========================================================
    // 2. REPORTE: TOTAL DE PRODUCTOS VENDIDOS
    // ==========================================================
    public Integer obtenerCantidadProductosVendidos(Date inicio, Date fin) {
        String sql = """
                    SELECT IFNULL(SUM(c.Cantidad), 0)
                    FROM carrito c
                    INNER JOIN pedido p ON c.IDPedido = p.IDPedido
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                """;

        return jdbcTemplate.queryForObject(sql, Integer.class, inicio, fin);
    }

    // ==========================================================
    // 3. REPORTE: CATEGORÍAS MÁS VENDIDAS
    // ==========================================================
    public List<Map<String, Object>> obtenerCategoriasMasVendidas(Date inicio, Date fin) {
        String sql = """
                    SELECT cat.NomCategoria,
                           IFNULL(SUM(c.Cantidad), 0) AS totalVendidos
                    FROM carrito c
                    INNER JOIN pedido p ON c.IDPedido = p.IDPedido
                    INNER JOIN producto prod ON prod.IDProducto = c.IDProducto
                    INNER JOIN productohistoria h ON h.IDProdHistoria = prod.IDProdHistoria
                    INNER JOIN categoriaproducto cat ON cat.IDCategoria = h.IDCategoria
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                    GROUP BY cat.NomCategoria
                    ORDER BY totalVendidos DESC
                """;

        return jdbcTemplate.queryForList(sql, inicio, fin);
    }

    // ==========================================================
    // 4. REPORTE: CLIENTES FRECUENTES
    // ==========================================================
    public List<Map<String, Object>> obtenerClientesFrecuentes(Date inicio, Date fin) {
        String sql = """
                    SELECT cl.Nombre, cl.Apellido,
                           COUNT(p.IDPedido) AS pedidosRealizados
                    FROM pedido p
                    INNER JOIN cliente cl ON cl.IDCliente = p.IDCliente
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                    GROUP BY cl.IDCliente
                    ORDER BY pedidosRealizados DESC
                    LIMIT 10
                """;

        return jdbcTemplate.queryForList(sql, inicio, fin);
    }

    // ==========================================================
    // 5. REPORTE: INGRESO TOTAL POR DÍA
    // ==========================================================
    public Double obtenerIngresosPorDia(Date fecha) {
        String sql = """
                    SELECT IFNULL(SUM(p.MontoFinal), 0)
                    FROM pedido p
                    WHERE DATE(p.FechaPedido) = DATE(?)
                      AND p.EstadoPedido = 'Pagado'
                """;

        return jdbcTemplate.queryForObject(sql, Double.class, fecha);
    }

    // ==========================================================
    // 6. REPORTE: EVOLUCIÓN TEMPORAL
    // ==========================================================
    public List<Map<String, Object>> obtenerEvolucion(Date inicio, Date fin) {

        String sql = """
                    SELECT
                        DATE(p.FechaPedido) AS fecha,
                        SUM(COALESCE(p.MontoFinal, 0)) AS total
                    FROM pedido p
                    WHERE DATE(p.FechaPedido) BETWEEN DATE(?) AND DATE(?)
                      AND p.EstadoPedido = 'Pagado'
                    GROUP BY DATE(p.FechaPedido)
                    ORDER BY fecha ASC
                """;

        return jdbcTemplate.queryForList(sql, inicio, fin);
    }

    // ==========================================================
    // 7. REPORTE DETALLADO
    // ==========================================================
    public List<Map<String, Object>> obtenerDetalleReporte(Date inicio, Date fin) {
        String sql = """
                    SELECT p.FechaPedido,
                           CONCAT('Pedido #', p.IDPedido) AS detalle,
                           (
                                SELECT IFNULL(SUM(c.Cantidad), 0)
                                FROM carrito c
                                WHERE c.IDPedido = p.IDPedido
                           ) AS movimientos,
                           p.MontoFinal AS total
                    FROM pedido p
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                    ORDER BY p.FechaPedido DESC
                """;

        return jdbcTemplate.queryForList(sql, inicio, fin);
    }
}
