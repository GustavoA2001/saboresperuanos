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

    /**
     * Método para agregar un producto al carrito asociado a un pedido
     */
    public void guardarEnCarrito(Long idCliente, Long idProducto, int cantidad, double precioUnitario,
            Integer idPedido) {
        // Insertar producto en el carrito
        String sqlCarrito = """
                    INSERT INTO carrito (IDPedido, IDProducto, Cantidad, PrecioProducto)
                    VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sqlCarrito, idPedido, idProducto, cantidad, precioUnitario);

        // Actualizar el monto final del pedido
        String sqlActualizarMonto = """
                    UPDATE pedido p
                    SET p.MontoFinal = (
                        SELECT SUM(c.Cantidad * c.PrecioProducto)
                        FROM carrito c
                        WHERE c.IDPedido = ?
                    )
                    WHERE p.IDPedido = ?
                """;
        jdbcTemplate.update(sqlActualizarMonto, idPedido, idPedido);
    }

    /**
     * Obtener el último pedido activo de un cliente
     */
    public Integer obtenerUltimoPedidoPorCliente(Long idCliente) {
        String sql = """
                    SELECT IDPedido
                    FROM pedido
                    WHERE IDCliente = ?
                    AND DATE(FechaPedido) = CURDATE()
                    AND EstadoPedido = 'Activo'
                    ORDER BY FechaPedido DESC
                    LIMIT 1
                """;
        List<Integer> resultados = jdbcTemplate.queryForList(sql, new Object[] { idCliente }, Integer.class);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /**
     * Crear un nuevo pedido activo para el cliente
     */
    public Integer crearNuevoPedido(Long idCliente) {
        String sqlPedido = """
                    INSERT INTO pedido (IDCliente, FechaPedido, MontoFinal, EstadoPedido)
                    VALUES (?, NOW(), 0, 'Activo')
                """;
        jdbcTemplate.update(sqlPedido, idCliente);

        // Obtener el ID del nuevo pedido
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    /**
     * Obtener detalles del carrito
     */
    /**
     * Obtener detalles del carrito
     */
    public List<Carrito> obtenerDetallesCarrito(int idPedido) {
        String sql = """
                    SELECT
                        p.IDProducto,
                        ph.NomProducto AS producto,
                        c.Cantidad AS cantidad,
                        p.PrecioUnitario AS precioUnitario,
                        (c.Cantidad * p.PrecioUnitario) AS totalProducto,
                        c.IDCarrito AS idCarrito,
                        c.IDPedido AS idPedido,
                        COALESCE(fp.FotoPrincipal, 'default.jpg') AS fotoProducto,
                        pe.MontoFinal AS totalCarrito,
                        cli.Nombre AS clienteNombre,
                        cli.Apellido AS clienteApellido
                    FROM carrito c
                    JOIN producto p ON c.IDProducto = p.IDProducto
                    LEFT JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                    LEFT JOIN fotoproducto fp ON p.IDProdHistoria = fp.IDProdHistoria
                    JOIN pedido pe ON c.IDPedido = pe.IDPedido
                    JOIN cliente cli ON pe.IDCliente = cli.IDCliente
                    WHERE c.IDPedido = ?
                """;

        return jdbcTemplate.query(sql, new Object[] { idPedido }, new BeanPropertyRowMapper<>(Carrito.class));
    }

    /**
     * Eliminar producto del carrito y actualizar el monto
     */
    public void eliminarProducto(Long idCarrito, Long idPedido) {
        String sql = "DELETE FROM carrito WHERE IDCarrito = ? AND IDPedido = ?";
        jdbcTemplate.update(sql, idCarrito, idPedido);

        String sqlActualizarMonto = """
                    UPDATE pedido p
                    SET p.MontoFinal = (
                        SELECT IFNULL(SUM(c.Cantidad * c.PrecioProducto), 0)
                        FROM carrito c
                        WHERE c.IDPedido = ?
                    )
                    WHERE p.IDPedido = ?
                """;
        jdbcTemplate.update(sqlActualizarMonto, idPedido, idPedido);
    }
}
