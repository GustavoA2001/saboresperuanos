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
        List<Productos> productos = jdbcTemplate.query(sql, new Object[]{idProducto}, getRowMapper());
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

    public int countByCategoria(Long categoriaId) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE IDCategoria = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{categoriaId}, Integer.class);
    }

    public int countByBusquedaPorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE NomProducto LIKE ?";
        String param = "%" + nombre + "%";
        return jdbcTemplate.queryForObject(sql, new Object[]{param}, Integer.class);
    }

    /* ===================== CONSULTAS DE LISTAS ===================== */

    public List<Productos> findAll() {
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
        """;
        return jdbcTemplate.query(sql, getRowMapper());
    }

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
                p.IDCategoria
            FROM producto p
            LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
            LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
            LIMIT ? OFFSET ?
        """;
        return jdbcTemplate.query(sql, new Object[]{limit, offset}, getRowMapper());
    }

    /* ===================== CONSULTAS PERSONALIZADAS ===================== */

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

        return jdbcTemplate.query(sql, new Object[]{categoria}, new BeanPropertyRowMapper<>(Productos.class));
    }

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
        return jdbcTemplate.queryForObject(sql, new Object[]{idProducto}, new BeanPropertyRowMapper<>(Productos.class));
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
        return jdbcTemplate.query(sql, new Object[]{categoriaId, limit, offset}, getRowMapper());
    }

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
        return jdbcTemplate.query(sql, new Object[]{param, limit, offset}, getRowMapper());
    }

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
        return jdbcTemplate.query(sql, new Object[]{param}, getRowMapper());
    }

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
        return jdbcTemplate.query(sql, new Object[]{idCategoria}, getRowMapper());
    }

    public Productos obtenerPorId(int idProducto) {
        String sql = """
            SELECT
                p.IDProducto AS idProducto,
                h.NomProducto AS nomProducto,
                p.PrecioUnitario AS precioUnitario,
                h.Descripcion AS descripcion,
                p.Cantidad AS cantidad,
                p.FechaProducto AS fechaProducto,
                f.FotoPrincipal AS fotoProducto,
                p.IDCategoria AS idCategoria
            FROM producto p
            LEFT JOIN productohistoria h ON p.IDProducto = h.IDProducto
            LEFT JOIN fotoproducto f ON p.IDProducto = f.IDProducto
            WHERE p.IDProducto = ?
        """;

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Productos.class), idProducto);
    }
}
