package com.app.restaurante.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.restaurante.model.Carrito;

@Repository
public class CarritoDAO {
    private final JdbcTemplate jdbcTemplate;

    public CarritoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected String getTableName() {
        return "carrito";
    }

    /*
     * Metodo para agregar un producto al carrito asociado a un pedido
     * SE USA EN CARRITO
     */
    public void guardarEnCarrito(Long idCliente, Long idProducto, int cantidad, double precioUnitario, Integer idPedido) {
        // Insertar el producto en el carrito
        String sqlCarrito = "INSERT INTO carrito (IDPedido, IDProducto, Cantidad, PrecioProducto) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlCarrito, idPedido, idProducto, cantidad, precioUnitario);

        // Actualizar el monto final del pedido sumando todos los productos del carrito
        String sqlActualizarMonto = "UPDATE pedido p SET p.MontoFinal = (SELECT SUM(c.PrecioProducto) FROM carrito c WHERE c.IDPedido = ?) WHERE p.IDPedido = ?";
        jdbcTemplate.update(sqlActualizarMonto, idPedido, idPedido);
    }

    /**
     * Metodo para obtener el último ID de pedido de un cliente
     */
    @SuppressWarnings("deprecation")
    public Integer obtenerUltimoPedidoPorCliente(Long idCliente) {
        String sql = "SELECT IDPedido FROM pedido WHERE IDCliente = ? AND DATE(FechaPedido) = CURDATE() AND Estado = 'Activo' ORDER BY FechaPedido DESC LIMIT 1";
        List<Integer> resultados = jdbcTemplate.queryForList(sql, new Object[]{idCliente}, Integer.class);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /**
     * Metodo para crear un nuevo pedido para el cliente
     * SE USA EN CARRITO
     */
    public Integer crearNuevoPedido(Long idCliente) {
        String sqlPedido = "INSERT INTO pedido (IDCliente, FechaPedido, MontoFinal, Estado) VALUES (?, NOW(), 0, 'Activo')";
        jdbcTemplate.update(sqlPedido, idCliente);

        // Obtener el ID del nuevo pedido
        String sql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Metodo para obtener detalles completos del carrito por ID de pedido
     */
    @SuppressWarnings("deprecation")
    public List<Carrito> obtenerDetallesCarrito(int idPedido) {
        String sql = "SELECT p.NomProducto AS Producto, c.Cantidad AS Cantidad, " +
                    "p.PrecioUnitario AS PrecioUnitario, (c.Cantidad * p.PrecioUnitario) AS TotalProducto, " +
                    "c.IDCarrito, c.IDProducto, c.IDPedido, pe.MontoFinal AS TotalCarrito, " +
                    "cli.Nombre AS ClienteNombre, cli.Apellido AS ClienteApellido " +                
                    "FROM carrito c " +
                    "JOIN producto p ON c.IDProducto = p.IDProducto " +
                    "JOIN pedido pe ON c.IDPedido = pe.IDPedido " +
                    "JOIN cliente cli ON pe.IDCliente = cli.IDCliente " +
                    "WHERE c.IDPedido = ?";

        return jdbcTemplate.query(sql, new Object[]{idPedido}, new BeanPropertyRowMapper<>(Carrito.class));
    }

    /*
    * Metodo para eliminar un producto del carrito
    */
    public void eliminarProducto(Long idCarrito, Long idPedido) {
        String sql = "DELETE FROM carrito WHERE idCarrito = ? AND idPedido = ?";
        jdbcTemplate.update(sql, idCarrito, idPedido);

        // SE REPITE CREAR UN METODO PARA UTLIZARLO
        // Actualizar el monto final del pedido sumando todos los productos del carrito
        String sqlActualizarMonto = "UPDATE pedido p SET p.MontoFinal = (SELECT SUM(c.PrecioProducto) FROM carrito c WHERE c.IDPedido = ?) WHERE p.IDPedido = ?";
        jdbcTemplate.update(sqlActualizarMonto, idPedido, idPedido);
    }

}
