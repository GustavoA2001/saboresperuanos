package com.app.restaurante.dao;

import com.app.restaurante.model.Categoria;
import com.app.restaurante.model.Productos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class ProductosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductosDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected String getTableName() {
        return "producto";
    }

    protected RowMapper<Productos> getRowMapper() {
        return new RowMapper<Productos>() {
            @Override
            public Productos mapRow(ResultSet rs, int rowNum) throws SQLException {
                Productos productos = new Productos();
                productos.setIdProducto(rs.getLong("IDProducto"));
                productos.setNomProducto(rs.getString("NomProducto"));
                productos.setPrecioUnitario(rs.getDouble("PrecioUnitario"));
                productos.setFotoProducto(rs.getString("FotoProducto"));
                productos.setDescripcion(rs.getString("Descripcion"));
                productos.setCantidad(rs.getInt("Cantidad"));
                productos.setIdCategoria(rs.getLong("IDCategoria"));
                productos.setFechaProducto(rs.getString("FechaProducto"));
                return productos;
            }
        };
    }

    /* ===================== GUARDAR O ACTUALIZAR ===================== */
    public void save(Productos producto) {
        if (producto.getIdProducto() == null) {
            String sql = "INSERT INTO " + getTableName()
                    + " (NomProducto, PrecioUnitario, Descripcion, Cantidad, FechaProducto, FotoProducto, IDCategoria) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    producto.getNomProducto(),
                    producto.getPrecioUnitario(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    producto.getFechaProducto(),
                    producto.getFotoProducto(),
                    producto.getIdCategoria());
        } else {
            String sql = "UPDATE " + getTableName()
                    + " SET NomProducto = ?, PrecioUnitario = ?, Descripcion = ?, "
                    + "Cantidad = ?, FechaProducto = ?, FotoProducto = ?, IDCategoria = ? "
                    + "WHERE IDProducto = ?";
            jdbcTemplate.update(sql,
                    producto.getNomProducto(),
                    producto.getPrecioUnitario(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    producto.getFechaProducto(),
                    producto.getFotoProducto(),
                    producto.getIdCategoria(),
                    producto.getIdProducto());
        }
    }

    /* ===================== BÚSQUEDAS ===================== */
    @SuppressWarnings("deprecation")
    public Productos findById(Long idProducto) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE IDProducto = ?";
        List<Productos> productos = jdbcTemplate.query(sql, new Object[] { idProducto }, getRowMapper());
        return productos.isEmpty() ? null : productos.get(0);
    }

    /* ===================== ELIMINACIONES ===================== */
    public void deleteById(Long idProducto) {
        String sql = "DELETE FROM " + getTableName() + " WHERE IDProducto = ?";
        jdbcTemplate.update(sql, idProducto);
    }

    public void deleteAll() {
        String sql = "DELETE FROM " + getTableName();
        jdbcTemplate.update(sql);
    }

    public void deleteByCategoria(Long idCategoria) {
        String sql = "DELETE FROM " + getTableName() + " WHERE IDCategoria = ?";
        jdbcTemplate.update(sql, idCategoria);
    }

    /* ===================== CONTADORES ===================== */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @SuppressWarnings("deprecation")
    public int countByCategoria(Long categoriaId) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE IDCategoria = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { categoriaId }, Integer.class);
    }

    @SuppressWarnings("deprecation")
    public int countByBusquedaPorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE NomProducto LIKE ?";
        String param = "%" + nombre + "%";
        return jdbcTemplate.queryForObject(sql, new Object[] { param }, Integer.class);
    }

    /* ===================== CONSULTAS DE LISTAS ===================== */

    public List<Productos> findAll() {
        String sql = """
                    SELECT
                        p.IDProducto AS IDProducto,
                        COALESCE(h.NomProducto, '') AS NomProducto,
                        COALESCE(h.Descripcion, '') AS Descripcion,
                        p.PrecioUnitario AS PrecioUnitario,
                        p.Cantidad AS Cantidad,
                        p.FechaProducto AS FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        h.IDCategoria AS IDCategoria
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                    LEFT JOIN fotoproducto f ON h.IDProdHistoria = f.IDProdHistoria
                    ORDER BY p.FechaProducto DESC
                """;

        return jdbcTemplate.query(sql, getRowMapper());
    }

    @SuppressWarnings("deprecation")
    public List<Productos> findAllProducts(int offset, int limit) {
        String sql = """
                SELECT
                    p.IDProducto,
                    h.NomProducto,
                    h.Descripcion,
                    p.PrecioUnitario,
                    p.Cantidad,
                    p.FechaProducto,
                    f.FotoPrincipal AS FotoProducto,
                    h.IDCategoria AS IDCategoria
                FROM producto p
                LEFT JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                LEFT JOIN fotoproducto f ON h.IDProdHistoria = f.IDProdHistoria
                LIMIT ? OFFSET ?
                """;
        return jdbcTemplate.query(sql, new Object[] { limit, offset }, getRowMapper());
    }

    /* ===================== CONSULTAS PERSONALIZADAS ===================== */

    @SuppressWarnings("deprecation")
    public List<Productos> obtenerPorCategoria(String categoria) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        c.NomCategoria,
                        p.IDCategoria
                    FROM producto p
                    INNER JOIN categoriaproducto c ON p.IDCategoria = c.IDCategoria
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE c.NomCategoria = ?
                """;

        return jdbcTemplate.query(sql, new Object[] { categoria }, new BeanPropertyRowMapper<>(Productos.class));
    }

    @SuppressWarnings("deprecation")
    public Productos obtenerProductoPorId(int idProducto) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        c.NomCategoria,
                        p.IDCategoria
                    FROM producto p
                    LEFT JOIN categoriaproducto c ON p.IDCategoria = c.IDCategoria
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE p.IDProducto = ?
                """;
        return jdbcTemplate.queryForObject(sql, new Object[] { idProducto },
                new BeanPropertyRowMapper<>(Productos.class));
    }

    public List<Categoria> findAllCategorias() {
        String sql = "SELECT IDCategoria, NomCategoria FROM categoriaproducto";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getLong("IDCategoria"));
            categoria.setNomCategoria(rs.getString("NomCategoria"));
            return categoria;
        });
    }

    @SuppressWarnings("deprecation")
    public List<Productos> obtenerPorCategoriaId(Long categoriaId, int offset, int limit) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        p.IDCategoria
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE p.IDCategoria = ?
                    LIMIT ? OFFSET ?
                """;
        return jdbcTemplate.query(sql, new Object[] { categoriaId, limit, offset }, getRowMapper());
    }

    @SuppressWarnings("deprecation")
    public List<Productos> buscarProductosPorNombre(String nombre, int offset, int limit) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        p.IDCategoria
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE h.NomProducto LIKE ?
                    LIMIT ? OFFSET ?
                """;
        String param = "%" + nombre + "%";
        return jdbcTemplate.query(sql, new Object[] { param, limit, offset }, getRowMapper());
    }

    @SuppressWarnings("deprecation")
    public List<Productos> buscarProductosPorNombre(String nombre) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        p.IDCategoria
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE h.NomProducto LIKE ?
                """;
        String param = "%" + nombre + "%";
        return jdbcTemplate.query(sql, new Object[] { param }, getRowMapper());
    }

    @SuppressWarnings("deprecation")
    public List<Productos> buscarProductosPorCategoria(Long idCategoria) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        h.NomProducto,
                        h.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.FechaProducto,
                        f.FotoPrincipal AS FotoProducto,
                        p.IDCategoria
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
                    LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
                    WHERE p.IDCategoria = ?
                """;
        return jdbcTemplate.query(sql, new Object[] { idCategoria }, getRowMapper());
    }

    public Productos obtenerPorId(int idProducto) {
        String sql = """
                    SELECT
                        p.IDProducto AS idProducto,
                        COALESCE(h.NomProducto, '') AS nomProducto,
                        COALESCE(h.Descripcion, '') AS descripcion,
                        p.PrecioUnitario AS precioUnitario,
                        p.Cantidad AS cantidad,
                        p.FechaProducto AS fechaProducto,
                        f.FotoPrincipal AS fotoProducto
                    FROM producto p
                    LEFT JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                    LEFT JOIN fotoproducto f ON h.IDProdHistoria = f.IDProdHistoria
                    WHERE p.IDProducto = ?
                """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Productos.class), idProducto);
    }

    // Productos de la carta
    public List<Map<String, Object>> obtenerProductosDiaAnterior() {
        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    h.NomProducto AS nomProducto,
                    p.PrecioUnitario AS precioUnitario,
                    p.Cantidad AS cantidadInicial,
                    COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = CURDATE() - INTERVAL 1 DAY
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL   -- solo pedidos con pago registrado
                    ), 0) AS vendidos,
                    (p.Cantidad - COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = CURDATE() - INTERVAL 1 DAY
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL
                    ), 0)) AS stock,
                    p.FechaProducto AS fechaProducto
                FROM producto p
                JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                WHERE DATE(p.FechaProducto) = CURDATE() - INTERVAL 1 DAY
                  AND h.EnCarta = 0
                  AND h.EstadoProducto = 'Activo'
                ORDER BY p.FechaProducto DESC;

                """;

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerProductosRecientes() {
        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    h.NomProducto AS nomProducto,
                    p.PrecioUnitario AS precioUnitario,
                    p.Cantidad AS cantidadInicial,
                    COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = CURDATE()
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL
                    ), 0) AS vendidos,
                    (p.Cantidad - COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = CURDATE()
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL
                    ), 0)) AS stock
                FROM producto p
                JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                WHERE DATE(p.FechaProducto) = CURDATE()
                  AND h.EnCarta = 1
                  AND h.EstadoProducto = 'Activo'
                ORDER BY p.FechaProducto DESC;
                """;

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obtenerProductosPasados() {
        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    h.NomProducto AS nomProducto,
                    p.PrecioUnitario AS precioUnitario,
                    p.Cantidad AS cantidadInicial,
                    COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = DATE(p.FechaProducto)
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL
                    ), 0) AS vendidos,
                    (p.Cantidad - COALESCE((
                        SELECT SUM(c.Cantidad)
                        FROM carrito c
                        JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        LEFT JOIN pago pa ON pe.IDPedido = pa.IDPedido
                        WHERE c.IDProducto = p.IDProducto
                          AND DATE(pe.FechaPedido) = DATE(p.FechaProducto)
                          AND pe.EstadoPedido = 'Pagado'
                          AND pa.IDPago IS NOT NULL
                    ), 0)) AS stock,
                    p.FechaProducto AS fechaProducto
                FROM producto p
                JOIN productohistoria h ON p.IDProdHistoria = h.IDProdHistoria
                JOIN (
                    -- Subquery: obtener la fecha más reciente de cada producto
                    SELECT IDProdHistoria, MAX(FechaProducto) AS maxFecha
                    FROM producto
                    WHERE FechaProducto < CURDATE() - INTERVAL 1 DAY
                    GROUP BY IDProdHistoria
                ) ultimos ON p.IDProdHistoria = ultimos.IDProdHistoria AND p.FechaProducto = ultimos.maxFecha
                WHERE h.EstadoProducto = 'Activo'
                ORDER BY p.FechaProducto DESC;

                                """;

        return jdbcTemplate.queryForList(sql);
    }

}
