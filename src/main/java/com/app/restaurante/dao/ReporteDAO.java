package com.app.restaurante.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ReporteDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReporteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =========================================================================
    // MÓDULO 1 — REPORTE GENERAL DEL SISTEMA
    // =========================================================================

    // 1. Ventas Totales
    public Double obtenerVentasTotales(Date inicio, Date fin) {
        String sql = """
                    SELECT IFNULL(SUM(p.MontoFinal), 0)
                    FROM pedido p
                    WHERE p.FechaPedido BETWEEN ? AND ?
                      AND p.EstadoPedido = 'Pagado'
                """;
        return jdbcTemplate.queryForObject(sql, Double.class, inicio, fin);
    }

    // 2. Total de productos vendidos
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

    // 3. Categorías más vendidas
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

    // 4. Clientes más frecuentes
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

    // 5. Ingreso total por día
    public Double obtenerIngresosPorDia(Date fecha) {
        String sql = """
                    SELECT IFNULL(SUM(p.MontoFinal), 0)
                    FROM pedido p
                    WHERE DATE(p.FechaPedido) = DATE(?)
                      AND p.EstadoPedido = 'Pagado'
                """;
        return jdbcTemplate.queryForObject(sql, Double.class, fecha);
    }

    // 6. Evolución temporal global
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

    // 7. Reporte detallado del sistema
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

    // 8. Reporte ventas por categoría o producto
    public List<Map<String, Object>> reporteVentas(Date inicio, Date fin, String atributo) {

        String sql;

        if ("producto".equalsIgnoreCase(atributo)) {
            sql = """
                        SELECT
                            ph.NomProducto AS etiqueta,
                            cp.NomCategoria AS categoria,
                            SUM(c.Cantidad) AS cantidadVendida,
                            SUM(c.Cantidad * c.PrecioProducto) AS ingresoTotal,
                            SUM((c.Cantidad * c.PrecioProducto) * 0.18) AS igv,
                            SUM((c.Cantidad * c.PrecioProducto) * 0.82) AS ingresoNeto
                        FROM carrito c
                        INNER JOIN producto p ON p.IDProducto = c.IDProducto
                        INNER JOIN productohistoria ph ON ph.IDProdHistoria = p.IDProdHistoria
                        INNER JOIN categoriaproducto cp ON cp.IDCategoria = ph.IDCategoria
                        INNER JOIN pedido pe ON pe.IDPedido = c.IDPedido
                        WHERE pe.FechaPedido BETWEEN ? AND ?
                          AND pe.EstadoPedido = 'Pagado'
                        GROUP BY ph.NomProducto, cp.NomCategoria
                        ORDER BY ingresoTotal DESC
                    """;
        } else {
            sql = """
                        SELECT
                            cp.NomCategoria AS etiqueta,
                            '' AS categoria,
                            SUM(c.Cantidad) AS cantidadVendida,
                            SUM(c.Cantidad * c.PrecioProducto) AS ingresoTotal,
                            SUM((c.Cantidad * c.PrecioProducto) * 0.18) AS igv,
                            SUM((c.Cantidad * c.PrecioProducto) * 0.82) AS ingresoNeto
                        FROM carrito c
                        INNER JOIN producto p ON p.IDProducto = c.IDProducto
                        INNER JOIN productohistoria ph ON ph.IDProdHistoria = p.IDProdHistoria
                        INNER JOIN categoriaproducto cp ON cp.IDCategoria = ph.IDCategoria
                        INNER JOIN pedido pe ON pe.IDPedido = c.IDPedido
                        WHERE pe.FechaPedido BETWEEN ? AND ?
                          AND pe.EstadoPedido = 'Pagado'
                        GROUP BY cp.NomCategoria
                        ORDER BY ingresoTotal DESC
                    """;
        }

        return jdbcTemplate.queryForList(sql, inicio, fin);
    }

    // =========================================================================
    // MÓDULO 2 — REPORTE POR PRODUCTO
    // =========================================================================

    // Listar productos
    public List<Map<String, Object>> listarProductos() {
        String sql = """
                    SELECT
                        h.IDProdHistoria AS idProdHistoria,
                        h.NomProducto AS nombre
                    FROM productohistoria h
                    ORDER BY h.NomProducto ASC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // Evolución de precios
    public List<Map<String, Object>> evolucionPreciosProducto(int idProdHistoria, LocalDate inicio, LocalDate fin) {
        String sql = """
                SELECT DATE(FechaProducto) AS fecha, PrecioUnitario AS precio
                FROM producto
                WHERE IDProdHistoria = ?
                  AND DATE(FechaProducto) BETWEEN ? AND ?
                ORDER BY FechaProducto ASC
                """;
        return jdbcTemplate.queryForList(sql, idProdHistoria, inicio.toString(), fin.toString());
    }

    // Evolución de cantidades
    public List<Map<String, Object>> evolucionCantidadProducto(int idProdHistoria, LocalDate inicio, LocalDate fin) {
        String sql = """
                SELECT DATE(FechaProducto) AS fecha, Cantidad AS cantidad
                FROM producto
                WHERE IDProdHistoria = ?
                  AND DATE(FechaProducto) BETWEEN ? AND ?
                ORDER BY FechaProducto ASC
                """;
        return jdbcTemplate.queryForList(sql, idProdHistoria, inicio.toString(), fin.toString());
    }

    // Ventas por producto
    public List<Map<String, Object>> ventasProducto(int idProdHistoria, LocalDate inicio, LocalDate fin) {
        String sql = """
                SELECT DATE(p.FechaPedido) AS fecha,
                       SUM(c.Cantidad) AS cantidad,
                       SUM(c.Cantidad * c.PrecioProducto) AS total
                FROM carrito c
                INNER JOIN pedido p ON p.IDPedido = c.IDPedido
                INNER JOIN producto pr ON pr.IDProducto = c.IDProducto
                WHERE pr.IDProdHistoria = ?
                  AND DATE(p.FechaPedido) BETWEEN ? AND ?
                  AND p.EstadoPedido = 'Pagado'
                GROUP BY DATE(p.FechaPedido)
                ORDER BY fecha ASC
                """;
        return jdbcTemplate.queryForList(sql, idProdHistoria, inicio.toString(), fin.toString());
    }

    // =========================================================================
    // MÓDULO 3 — AUDITORÍA DE PRODUCTOS
    // =========================================================================

    // Primer y último movimiento
    public Map<String, Object> obtenerPrimerUltimoMovimiento(int idProducto) {
        String sql = """
                    SELECT
                        MIN(p.FechaPedido) AS inicio,
                        MAX(p.FechaPedido) AS fin
                    FROM carrito c
                    INNER JOIN pedido p ON p.IDPedido = c.IDPedido
                    WHERE c.IDProducto = ?
                """;
        return jdbcTemplate.queryForMap(sql, idProducto);
    }

    // Bloques generados desde auditoría BD (semana, mes, año)
    public List<Map<String, Object>> obtenerBloquesAuditoria(int idProducto, String tipo) {

        String sql = "";

        switch (tipo) {
            case "semana":
                sql = """
                          SELECT DATE(fecha) AS inicio,
                                 DATE(fecha + INTERVAL 6 DAY) AS fin
                          FROM auditoria_producto
                          WHERE idProducto = ?
                          GROUP BY YEARWEEK(fecha)
                          ORDER BY fecha DESC
                        """;
                break;

            case "mes":
                sql = """
                          SELECT DATE(DATE_FORMAT(fecha,'%Y-%m-01')) AS inicio,
                                 LAST_DAY(fecha) AS fin
                          FROM auditoria_producto
                          WHERE idProducto = ?
                          GROUP BY YEAR(fecha), MONTH(fecha)
                          ORDER BY fecha DESC
                        """;
                break;

            case "anio":
                sql = """
                          SELECT DATE(CONCAT(YEAR(fecha),'-01-01')) AS inicio,
                                 DATE(CONCAT(YEAR(fecha),'-12-31')) AS fin
                          FROM auditoria_producto
                          WHERE idProducto = ?
                          GROUP BY YEAR(fecha)
                          ORDER BY fecha DESC
                        """;
                break;
        }

        return jdbcTemplate.queryForList(sql, idProducto);
    }

    // Bloques generados manualmente (semana, mes, año, rango)
    public List<Map<String, Object>> generarBloques(int idProducto, String tipo, Date inicio, Date fin) {

        String sql = "";

        switch (tipo.toLowerCase()) {

            case "semana":
                sql = """
                          SELECT DATE(fecha) AS inicio,
                                 DATE(fecha) AS fin
                          FROM productoauditoria
                          WHERE IDProducto = ?
                          GROUP BY YEAR(fecha), WEEK(fecha)
                          ORDER BY fecha DESC
                          LIMIT 6
                        """;
                break;

            case "mes":
                sql = """
                          SELECT DATE_FORMAT(fecha, '%Y-%m-01') AS inicio,
                                 LAST_DAY(fecha) AS fin
                          FROM productoauditoria
                          WHERE IDProducto = ?
                          GROUP BY YEAR(fecha), MONTH(fecha)
                          ORDER BY fecha DESC
                          LIMIT 6
                        """;
                break;

            case "anio":
                sql = """
                          SELECT DATE_FORMAT(fecha, '%Y-01-01') AS inicio,
                                 DATE_FORMAT(fecha, '%Y-12-31') AS fin
                          FROM productoauditoria
                          WHERE IDProducto = ?
                          GROUP BY YEAR(fecha)
                          ORDER BY fecha DESC
                          LIMIT 5
                        """;
                break;

            case "rango":
                sql = """
                          SELECT ? AS inicio,
                                 ? AS fin
                          FROM DUAL
                        """;
                return jdbcTemplate.queryForList(sql, inicio, fin);
        }

        return jdbcTemplate.queryForList(sql, idProducto);
    }

    // Datos diarios para cada bloque
    public List<Map<String, Object>> obtenerDatosDiariosAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {

        System.out.println("[DAO] Consultando datos diarios...");
        System.out.println("   Producto: " + idProducto);
        System.out.println("   Inicio  : " + inicio);
        System.out.println("   Fin     : " + fin);

        String sql = """
                SELECT p.FechaPedido AS fecha,
                       SUM(c.Cantidad) AS cantidad,
                       SUM(c.Cantidad * c.PrecioProducto) AS total
                FROM carrito c
                INNER JOIN pedido p ON p.IDPedido = c.IDPedido
                WHERE c.IDProducto = ?
                  AND DATE(p.FechaPedido) BETWEEN ? AND ?
                GROUP BY DATE(p.FechaPedido)
                ORDER BY DATE(p.FechaPedido)
                """;

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, idProducto, inicio.toString(),
                fin.toString());

        System.out.println("   -> Registros encontrados: " + result.size());

        return result;
    }

    // Detalle completo de auditoría (por día)
    public List<Map<String, Object>> obtenerDetalleAuditoria(int idProducto, Date inicio, Date fin) {

        String sql = """
                SELECT p.FechaPedido AS fecha,
                       c.Cantidad,
                       c.PrecioProducto,
                       (c.Cantidad * c.PrecioProducto) AS subtotal,
                       p.IDPedido
                FROM carrito c
                INNER JOIN pedido p ON p.IDPedido = c.IDPedido
                WHERE c.IDProducto = ?
                  AND DATE(p.FechaPedido) BETWEEN ? AND ?
                ORDER BY p.FechaPedido, p.IDPedido
                """;

        return jdbcTemplate.queryForList(sql,
                idProducto,
                new java.sql.Date(inicio.getTime()),
                new java.sql.Date(fin.getTime()));
    }

    // Detalle diario resumido
    public List<Map<String, Object>> obtenerDetalleAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {
        String sql = """
                SELECT DATE(p.FechaPedido) AS fecha,
                       SUM(c.Cantidad) AS cantidad,
                       AVG(c.PrecioProducto) AS precio,
                       SUM(c.Cantidad * c.PrecioProducto) AS subtotal
                FROM carrito c
                INNER JOIN pedido p ON p.IDPedido = c.IDPedido
                WHERE c.IDProducto = ?
                  AND DATE(p.FechaPedido) BETWEEN ? AND ?
                GROUP BY DATE(p.FechaPedido)
                ORDER BY DATE(p.FechaPedido)
                """;
        return jdbcTemplate.queryForList(sql, idProducto, inicio.toString(), fin.toString());
    }
}
