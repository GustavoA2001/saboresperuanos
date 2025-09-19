package com.app.restaurante.dao;

import com.app.restaurante.model.Categoria;
//import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Productos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones de base de datos relacionadas con
 * los productos
 */
@Repository
public class ProductosDAO {

    // Inyeccion de dependencia de JdbcTemplate para ejecutar consultas SQL
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor que recibe el JdbcTemplate
     */
    public ProductosDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * --------------------------------------
     */
    // Metodo que devuelve el nombre de la tabla en la base de datos donde se
    // almacenan los clientes
    protected String getTableName() {
        return "producto";
    }

    // Metodo que devuelve el RowMapper para mapear los resultados a objetos
    // Productos
    protected RowMapper<Productos> getRowMapper() {
        return new RowMapper<Productos>() {
            @Override
            public Productos mapRow(ResultSet rs, int rowNum) throws SQLException {
                Productos productos = new Productos();
                productos.setIdProducto(rs.getLong("idProducto"));
                productos.setNomProducto(rs.getString("NomProducto"));
                productos.setPrecioUnitario(rs.getDouble("PrecioUnitario"));
                productos.setFotoProducto(rs.getString("FotoProducto"));
                productos.setDescripcion(rs.getString("Descripcion"));
                productos.setCantidad(rs.getInt("Cantidad"));
                productos.setIdCategoria(rs.getLong("IDCategoria"));
                productos.setIdTipo(rs.getLong("IDTipo"));
                productos.setFechaProducto(rs.getString("FechaProducto"));
                return productos;
            }
        };
    }

    // Metodo para guardar o actualizar un producto
    public void save(Productos producto) {
        if (producto.getIdProducto() == null) {
            // Si el producto no tiene ID, es nuevo, hacemos INSERT
            String sql = "INSERT INTO " + getTableName() +
                    " (NomProducto, PrecioUnitario, Descripcion, Cantidad, FechaProducto, FotoProducto, IDCategoria, IDTipo) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    producto.getNomProducto(),
                    producto.getPrecioUnitario(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    producto.getFechaProducto(),
                    producto.getFotoProducto(),
                    producto.getIdCategoria(),
                    producto.getIdTipo());
        } else {
            // Si el producto tiene ID, es existente, hacemos UPDATE
            String sql = "UPDATE " + getTableName() +
                    " SET NomProducto = ?, PrecioUnitario = ?, Descripcion = ?, " +
                    "Cantidad = ?, FechaProducto = ?, FotoProducto = ?, " +
                    "IDCategoria = ?, IDTipo = ? WHERE idProducto = ?";
            jdbcTemplate.update(sql,
                    producto.getNomProducto(),
                    producto.getPrecioUnitario(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    producto.getFechaProducto(),
                    producto.getFotoProducto(),
                    producto.getIdCategoria(),
                    producto.getIdTipo(),
                    producto.getIdProducto());
        }
    }

    // BUSQUEDA DE PRODUCTOS
    // Metodo para buscar un producto por su ID
    @SuppressWarnings("deprecation")
    public Productos findById(Long idProducto) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE idProducto = ?";
        List<Productos> productos = jdbcTemplate.query(sql, new Object[] { idProducto }, getRowMapper());
        return productos.isEmpty() ? null : productos.get(0);
    }



    // ELIMACION

    // Metodo para eliminar un producto por su ID
    public void deleteById(Long idProducto) {
        String sql = "DELETE FROM " + getTableName() + " WHERE idProducto = ?";
        jdbcTemplate.update(sql, idProducto);
    }

    // Metodo para eliminar todos los productos
    public void deleteAll() {
        String sql = "DELETE FROM " + getTableName();
        jdbcTemplate.update(sql);
    }

    // Metodo para eliminar productos por categoría
    public void deleteByCategoria(Long idCategoria) {
        String sql = "DELETE FROM " + getTableName() + " WHERE IDCategoria = ?";
        jdbcTemplate.update(sql, idCategoria);
    }



    // Metodo para contar todos los productos
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /*
     * --------------------------------------
     */

    /*
     * Metodo para obtener todos los tipos de productos
     */
    public List<String> findAllTipos() {
        // Consulta SQL para obtener todos los tipos distintos
        String sql = "SELECT IDTipo, NomTipo FROM tipoproducto";
        return jdbcTemplate.queryForList(sql, String.class);
    }


    /**
     * Metodo para obtener productos por categoría
     */
    @SuppressWarnings("deprecation")
    public List<Productos> obtenerPorCategoria(String categoria) {
        // Consulta SQL para obtener productos por categoria
        String sql = "SELECT idProducto, NomProducto, PrecioUnitario, FotoProducto, Descripcion, Cantidad FROM producto WHERE idCategoria = (SELECT idCategoria FROM categoriaproducto WHERE NomCategoria = ?);";
        List<Productos> productos = jdbcTemplate.query(sql, new Object[] { categoria },
                new BeanPropertyRowMapper<>(Productos.class));

        // Verifica el contenido de los productos antes de retornarlos
        for (Productos producto : productos) {
            System.out.println(
                    "Producto: " + producto.getNomProducto() + ", FotoProducto: " + producto.getFotoProducto() +
                            ", Precio" + producto.getPrecioUnitario() + ", Cantidad" + producto.getCantidad() + "./");
        }

        return productos;
    }

    /**
     * Metodo para obtener un producto por su ID
     */
    @SuppressWarnings("deprecation")
    public Productos obtenerProductoPorId(int idProducto) {
        // Consulta SQL para obtener un producto específico por su ID
        String sql = "SELECT idProducto, NomProducto, PrecioUnitario, FotoProducto, Descripcion FROM producto WHERE idProducto = ?";
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

    // Metodo para obtener todos los productos
    public List<Productos> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return jdbcTemplate.query(sql, getRowMapper());
    }

    // Metodo para obtener productos con paginación
    public List<Productos> findAllPaginated(int offset, int limit) {
        String sql = "SELECT * FROM " + getTableName() + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[] { limit, offset }, getRowMapper());
    }

    // Metodo para obtener los productos por categoria
    public List<Productos> obtenerPorCategoriaId(Long categoriaId, int offset, int limit) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE IDCategoria = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[] { categoriaId, limit, offset }, getRowMapper());
    }

    // Metodo que cuenta las categorias en la tabla "PRODUCTOS"
    public int countByCategoria(Long categoriaId) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE IDCategoria = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { categoriaId }, Integer.class);
    }

    // BUSQUEDA POR NOMBRE (con paginación)
public List<Productos> buscarProductosPorNombre(String nombre, int offset, int limit) {
    String sql = "SELECT * FROM " + getTableName() + " WHERE NomProducto LIKE ? LIMIT ? OFFSET ?";
    String param = "%" + nombre + "%";
    return jdbcTemplate.query(sql, new Object[]{param, limit, offset}, getRowMapper());
}

// Contar productos por búsqueda de nombre
public int countByBusquedaPorNombre(String nombre) {
    String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE NomProducto LIKE ?";
    String param = "%" + nombre + "%";
    return jdbcTemplate.queryForObject(sql, new Object[]{param}, Integer.class);
}

public List<Productos> buscarProductosPorNombre(String nombre) {
    String sql = "SELECT * FROM " + getTableName() + " WHERE NomProducto LIKE ?";
    String param = "%" + nombre + "%";
    return jdbcTemplate.query(sql, new Object[]{param}, getRowMapper());
}
public List<Productos> buscarProductosPorCategoria(Long idCategoria) {
    String sql = "SELECT * FROM " + getTableName() + " WHERE idCategoria = ?";
    return jdbcTemplate.query(sql, new Object[]{idCategoria}, getRowMapper());
}


}

