package com.app.restaurante.dao;

import com.app.restaurante.model.Recibo;
import com.app.restaurante.model.Productos;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReciboDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
    public Recibo obtenerReciboPorIdPedido(int idPedido) {
        String sql = "SELECT " +
                     "c.idcliente, c.nombre, c.apellido, " +
                     "d.iddireccion, d.direccion, " +
                     "p.idpedido, " +
                     "pr.idproducto, pr.nomproducto, " +
                     "pr.PrecioUnitario as precioProducto, cp.cantidad, " +
                     "pg.idpago, pg.totalpago, pg.fechaPago " +
                     "FROM cliente c " +
                     "INNER JOIN direccion d ON c.idcliente = d.idcliente " +
                     "INNER JOIN pedido p ON c.idcliente = p.idcliente " +
                     "INNER JOIN carrito cp ON p.idpedido = cp.idpedido " +
                     "INNER JOIN producto pr ON cp.idproducto = pr.idproducto " +
                     "INNER JOIN pago pg ON p.idpedido = pg.idpedido " +
                     "WHERE p.idPedido = ? " +
                     "ORDER BY cp.idproducto";

        // Ejecutar la consulta para obtener los datos del pedido y productos
        List<Recibo> recibos = jdbcTemplate.query(sql, new Object[]{idPedido},
            new RowMapper<Recibo>() {
                @SuppressWarnings("null")
                public Recibo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // Crear un objeto Recibo
                    Recibo recibo = new Recibo();
                    recibo.setIdCliente(rs.getInt("idcliente"));
                    recibo.setNombre(rs.getString("nombre"));
                    recibo.setApellido(rs.getString("apellido"));
                    recibo.setDireccion(rs.getString("direccion"));
                    recibo.setIdPedido(rs.getInt("idpedido"));
                    recibo.setTotalPago(rs.getDouble("totalpago"));
                    
                    // Convertir la fechaPago de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fechaPago");
                    if (timestamp != null) {
                        recibo.setFechaPago(timestamp.toLocalDateTime());  // Establecer el valor en LocalDateTime
                    }

                    List<Productos> productosList = new ArrayList<>();

                    do {
                        Long idProducto = rs.getLong("idproducto");
                        String nomProducto = rs.getString("nomproducto");
                        Double precioProducto = rs.getDouble("precioproducto");
                        int cantidad = rs.getInt("cantidad");
                        
                        Productos producto = new Productos(idProducto, nomProducto, precioProducto, cantidad);
                        productosList.add(producto);
                    } while (rs.next());

                    // Asignar la lista de productos al recibo
                    recibo.setProductos(productosList);

                    return recibo;
                }
            });

        // Si no hay recibo, retornamos null
        if (recibos.isEmpty()) {
            return null;
        }

        // Devolver el recibo con los productos
        return recibos.get(0);
    }
}
