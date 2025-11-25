package com.app.restaurante.dao;

import com.app.restaurante.model.Categoria;
import com.app.restaurante.model.Productos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
                        h.IDCategoria
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
                        h.IDCategoria
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

    public List<Productos> obtenerPorCategoriaIdDisponibles(Long categoriaId, int offset, int limit) {
        String sql = """
                    SELECT
                        p.IDProducto AS idProducto,
                        ph.NomProducto AS nomProducto,
                        ph.Descripcion AS descripcion,
                        p.PrecioUnitario AS precioUnitario,
                        (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS cantidad,
                        fp.FotoPrincipal AS fotoProducto,
                        ph.IDCategoria AS idCategoria,
                        p.FechaProducto AS FechaProducto
                    FROM producto p
                    INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                    LEFT JOIN fotoproducto fp ON ph.IDProdHistoria = fp.IDProdHistoria
                    LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                    LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                    WHERE p.EnCarta = 1
                      AND p.Visible = 1
                      AND DATE(p.FechaProducto) = CURDATE()
                      AND p.EstadoDia = 'disponible'
                      AND ph.IDCategoria = ?   -- <-- CORREGIDO
                    GROUP BY p.IDProducto
                    HAVING cantidad > 0
                    LIMIT ? OFFSET ?;
                """;
        return jdbcTemplate.query(sql, new Object[] { categoriaId, limit, offset }, getRowMapper());
    }

    public List<Productos> buscarProductosPorNombreDisponibles(String nombre, int offset, int limit) {
        String sql = """
                    SELECT
                        p.IDProducto AS idProducto,
                        ph.NomProducto AS nomProducto,
                        ph.Descripcion AS descripcion,
                        p.PrecioUnitario AS precioUnitario,
                        (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS cantidad,
                        fp.FotoPrincipal AS fotoProducto,
                        ph.IDCategoria AS idCategoria,
                        p.FechaProducto AS FechaProducto
                    FROM producto p
                    INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                    LEFT JOIN fotoproducto fp ON ph.IDProdHistoria = fp.IDProdHistoria
                    LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                    LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                    WHERE p.EnCarta = 1
                      AND p.Visible = 1
                      AND DATE(p.FechaProducto) = CURDATE()
                      AND p.EstadoDia = 'disponible'
                      AND ph.NomProducto LIKE ?  -- <-- ESTÁ BIEN
                    GROUP BY p.IDProducto
                    HAVING cantidad > 0
                    LIMIT ? OFFSET ?;
                """;
        String param = "%" + nombre + "%";
        return jdbcTemplate.query(sql, new Object[] { param, limit, offset }, getRowMapper());
    }

    public int countPorCategoriaDisponibles(Long categoriaId) {
        String sql = """
                    SELECT COUNT(*) FROM (
                        SELECT p.IDProducto,
                               (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS cantidad
                        FROM producto p
                        INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                        LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                        LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        WHERE p.EnCarta = 1
                          AND p.Visible = 1
                          AND DATE(p.FechaProducto) = CURDATE()
                          AND p.EstadoDia = 'disponible'
                          AND ph.IDCategoria = ?
                        GROUP BY p.IDProducto
                        HAVING cantidad > 0
                    ) AS sub;
                """;

        return jdbcTemplate.queryForObject(sql, new Object[] { categoriaId }, Integer.class);
    }

    public int countBusquedaDisponibles(String nombre) {
        String sql = """
                    SELECT COUNT(*) FROM (
                        SELECT p.IDProducto,
                               (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS cantidad
                        FROM producto p
                        INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                        LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                        LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                        WHERE p.EnCarta = 1
                          AND p.Visible = 1
                          AND DATE(p.FechaProducto) = CURDATE()
                          AND p.EstadoDia = 'disponible'
                          AND ph.NomProducto LIKE ?
                        GROUP BY p.IDProducto
                        HAVING cantidad > 0
                    ) AS sub;
                """;
        String param = "%" + nombre + "%";
        return jdbcTemplate.queryForObject(sql, new Object[] { param }, Integer.class);
    }

    public Productos obtenerPorId(int idProducto) {
        String sql = """
                    SELECT
                        p.IDProducto AS idProducto,
                        COALESCE(ph.NomProducto, '') AS nomProducto,
                        COALESCE(ph.Descripcion, '') AS descripcion,
                        p.PrecioUnitario AS precioUnitario,
                        p.Cantidad AS cantidadInicial,
                        IFNULL(SUM(c.Cantidad), 0) AS vendidos,
                        (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS stock,
                        p.Visible AS visible,
                        p.EnCarta AS enCarta,
                        p.EstadoDia AS estadoDia,
                        f.FotoPrincipal AS fotoProducto,
                        cat.NomCategoria AS nomCategoria
                    FROM producto p
                    INNER JOIN productohistoria ph ON ph.IDProdHistoria = p.IDProdHistoria
                    LEFT JOIN fotoproducto f ON f.IDProdHistoria = ph.IDProdHistoria
                    LEFT JOIN categoriaproducto cat ON cat.IDCategoria = ph.IDCategoria
                    LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                    LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido

                    WHERE p.IDProducto = ?
                    GROUP BY
                        p.IDProducto,
                        ph.NomProducto,
                        ph.Descripcion,
                        p.PrecioUnitario,
                        p.Cantidad,
                        p.Visible,
                        p.EnCarta,
                        p.EstadoDia,
                        f.FotoPrincipal,
                        cat.NomCategoria
                """;

        System.out.println("Ejecutando SQL para obtener producto por ID: " + idProducto);

        try {
            Productos producto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Productos.class),
                    idProducto);
            System.out.println("Producto recuperado desde DB: " + producto.getNomProducto() + ", stock calculado: "
                    + producto.getCantidad());
            return producto;
        } catch (Exception e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public List<Productos> findProductosDisponiblesHoy(int offset, int limit) {
        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    ph.NomProducto AS nomProducto,
                    ph.Descripcion AS descripcion,
                    p.PrecioUnitario AS precioUnitario,
                    (p.Cantidad - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) AS cantidad,
                    fp.FotoPrincipal AS fotoProducto,
                    ph.IDCategoria AS idCategoria,
                    p.FechaProducto AS FechaProducto
                FROM producto p
                INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                LEFT JOIN fotoproducto fp ON ph.IDProdHistoria = fp.IDProdHistoria
                LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                WHERE p.EnCarta = 1
                  AND p.Visible = 1
                  AND DATE(p.FechaProducto) = CURDATE()
                  AND p.EstadoDia = 'disponible'
                GROUP BY p.IDProducto
                HAVING cantidad > 0
                LIMIT ? OFFSET ?;

                    """;
        return jdbcTemplate.query(sql, new Object[] { limit, offset }, getRowMapper());
    }

    public int countProductosDisponiblesHoy() {
        String sql = """
                SELECT COUNT(*) FROM (
                    SELECT p.IDProducto
                    FROM producto p
                    LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                    LEFT JOIN pedido pe ON c.IDPedido = pe.IDPedido
                    WHERE p.EnCarta = 1
                      AND p.Visible = 1
                      AND DATE(p.FechaProducto) = CURDATE()
                      AND p.EstadoDia = 'disponible'
                    GROUP BY p.IDProducto, p.Cantidad
                    HAVING (MAX(p.Cantidad) - IFNULL(SUM(CASE WHEN pe.EstadoPedido='Pagado' THEN c.Cantidad ELSE 0 END), 0)) > 0
                ) AS sub;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // =============================================
    // CARTA PARA ADMINA
    // =============================================

    // =============================================
    // PRODUCTOS PARA LA CARTA (HOY)
    // =============================================
    public List<Map<String, Object>> obtenerProductosHoy() {
        System.out.println("\n---- [DAO] obtenerProductosHoy() ----");

        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    ph.NomProducto AS nomProducto,
                    ph.Descripcion AS descripcion,
                    p.PrecioUnitario AS precioUnitario,
                    p.Cantidad AS cantidadInicial,
                    IFNULL(SUM(c.Cantidad), 0) AS vendidos,
                    (p.Cantidad - IFNULL(SUM(c.Cantidad), 0)) AS stock,
                    p.Visible AS visible,
                    p.EnCarta AS enCarta,
                    p.EstadoDia AS estadoDia,
                    fp.FotoPrincipal AS fotoPrincipal,
                    cat.NomCategoria AS nomCategoria
                FROM producto p
                INNER JOIN productohistoria ph ON ph.IDProdHistoria = p.IDProdHistoria
                LEFT JOIN fotoproducto fp ON fp.IDProdHistoria = ph.IDProdHistoria
                LEFT JOIN categoriaproducto cat ON cat.IDCategoria = ph.IDCategoria
                LEFT JOIN carrito c ON c.IDProducto = p.IDProducto
                WHERE DATE(p.FechaProducto) = CURDATE()
                  AND p.EnCarta = 1
                GROUP BY
                    p.IDProducto,
                    ph.NomProducto,
                    ph.Descripcion,
                    p.PrecioUnitario,
                    p.Cantidad,
                    p.Visible,
                    p.EnCarta,
                    p.EstadoDia,
                    fp.FotoPrincipal,
                    cat.NomCategoria;
                    """;

        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql);

        System.out.println("Resultado HOY (rows): " + lista.size());

        int index = 1;
        for (Map<String, Object> row : lista) {
            System.out.println("\n---- Fila #" + index + " ----");
            row.forEach((k, v) -> {
                System.out.println(" " + k + " → " + v + " (tipo: " +
                        (v != null ? v.getClass().getSimpleName() : "NULL") + ")");
            });
            index++;
        }

        System.out.println("----------------------------------------\n");

        return lista;
    }

    // =============================================
    // HISTÓRICOS → Productos de días anteriores
    // Solo 1 por producto (último de cada día anterior)
    // Incluye vendidos reales del día (solo pedidos pagados)
    // =============================================
    public List<Map<String, Object>> obtenerProductosHistoricos() {

        System.out.println("---- [DAO] obtenerProductosHistoricos() (MODAL) ----");

        String sql = """
                SELECT
                    p.IDProducto AS idProducto,
                    ph.IDProdHistoria AS idProdHistoria,
                    ph.NomProducto AS nomProducto,
                    p.PrecioUnitario AS precioUnitario,
                    p.Cantidad AS cantidadInicial,

                    -- VENDIDOS reales del día
                    IFNULL((
                        SELECT SUM(c2.Cantidad)
                        FROM carrito c2
                        INNER JOIN pedido ped ON ped.IDPedido = c2.IDPedido
                        WHERE c2.IDProducto = p.IDProducto
                          AND ped.EstadoPedido = 'Pagado'
                          AND DATE(p.FechaProducto) = DATE(ped.FechaPedido)
                    ), 0) AS vendidos,

                    DATE(p.FechaProducto) AS fechaProducto

                FROM producto p
                INNER JOIN productohistoria ph ON ph.IDProdHistoria = p.IDProdHistoria

                -- Último registro histórico por producto
                INNER JOIN (
                    SELECT
                        IDProdHistoria,
                        MAX(FechaProducto) AS maxFecha
                    FROM producto
                    WHERE DATE(FechaProducto) < CURDATE()
                    GROUP BY IDProdHistoria
                ) ult ON p.IDProdHistoria = ult.IDProdHistoria
                   AND p.FechaProducto = ult.maxFecha

                --EXCLUIR PRODUCTOS QUE YA ESTÁN EN CARTA HOY
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM producto p2
                    WHERE p2.IDProdHistoria = p.IDProdHistoria
                      AND DATE(p2.FechaProducto) = CURDATE()
                      AND p2.EnCarta = 1
                )

                ORDER BY p.FechaProducto DESC;
                                """;

        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql);

        System.out.println("Resultado para MODAL (históricos): " + lista.size());
        lista.forEach(row -> System.out.println("Fila: " + row));

        return lista;
    }

    public Map<String, Object> validarProductoActivo(Integer idProdHistoria) {

        System.out.println("DAO >> validarProductoActivo(" + idProdHistoria + ")");

        String sql = """
                    SELECT *
                    FROM productohistoria
                    WHERE IDProdHistoria = ?
                      AND EstadoProducto = 'Activo'
                """;

        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql, idProdHistoria);

        System.out.println("DAO >> Resultado validarProductoActivo: " + lista);

        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean existeProductoHoy(Integer idProdHistoria) {

        System.out.println("DAO >> existeProductoHoy(" + idProdHistoria + ")");

        String sql = """
                    SELECT COUNT(*)
                    FROM producto
                    WHERE IDProdHistoria = ?
                      AND DATE(FechaProducto) = CURDATE()
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idProdHistoria);

        System.out.println("DAO >> count = " + count);

        return count != null && count > 0;
    }

    public void insertarProductoHoy(Integer idProdHistoria, BigDecimal precio, Integer cantidad) {

        System.out.println("DAO >> insertarProductoHoy(id=" + idProdHistoria +
                ", precio=" + precio + ", cantidad=" + cantidad + ")");

        String sql = """
                    INSERT INTO producto (IDProdHistoria, PrecioUnitario, Cantidad, FechaProducto, EnCarta, Visible, EstadoDia)
                    VALUES (?, ?, ?, NOW(), 1, 0, 'disponible')
                """;

        jdbcTemplate.update(sql, idProdHistoria, precio, cantidad);

        System.out.println("DAO >> INSERT OK");
    }

    // Cambiar visibilidad
    public void actualizarVisible(int idProducto, int estado) {
        jdbcTemplate.update("UPDATE producto SET Visible = ? WHERE IDProducto = ?", estado, idProducto);
    }

    // Eliminar instancia
    public void eliminarProducto(int idProducto) {
        jdbcTemplate.update("DELETE FROM producto WHERE IDProducto = ?", idProducto);
    }

    // Cerrar carta del día
    public void cerrarCartaHoy() {
        jdbcTemplate
                .update("UPDATE producto SET Visible = 0, EstadoDia = 'cerrado' WHERE DATE(FechaProducto) = CURDATE()");
    }

}
