package com.app.restaurante.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.restaurante.model.Pedido;


@Repository
public class DeliveryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected String getTableName() {
        return "delivery"; // Nombre de la tabla en la base de datos
    }

    
    
    // Consulta para crear el delivery de un pedido        
    public void registrarDelivery(Long idpedido) {        
        String sqlDelivery = "INSERT INTO delivery (Estado, FechaDelivery, IDPedido) VALUES (?, NOW(), ?)";
        jdbcTemplate.update(sqlDelivery, "Iniciado", idpedido);
    }


     // Metodo para obtener los pedidos disponibles para delivery
    public List<Pedido> obtenerPedidosDisponibles() {
        String sql = "SELECT c.idcliente, c.nombre, c.apellido, " +
                     "d.iddireccion, d.direccion, " +
                     "p.idpedido, pg.idpago, pg.totalpago, pg.FechaPago, p.Estado as EstadoPedido, " +
                     "e.IDEmpleado, pg.fechaPago AS fechaPagoUltimo " +
                     "FROM cliente c " +
                     "INNER JOIN direccion d ON c.idcliente = d.idcliente " +
                     "INNER JOIN pedido p ON c.idcliente = p.idcliente " +
                     "INNER JOIN pago pg ON p.idpedido = pg.idpedido " +
                     "LEFT JOIN delivery e ON p.idpedido = e.IDPedido " +
                     "WHERE p.Estado = 'Pagado' AND e.IDEmpleado IS NULL AND e.IDDelivery IS NOT NULL " +
                     "GROUP BY p.IDPedido";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));
    }

    // Método para obtener el idDelivery asociado al idPedido
    public Integer obtenerIdDeliveryPorIdPedido(Integer idPedido) {
        String sql = "SELECT IDDelivery FROM Delivery where idPedido = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, idPedido);
        } catch (Exception e) {
            return null;  // Si no se encuentra el delivery, retornamos null
        }
    }

    // Método para registrar el idEmpleado en la tabla delivery
    public boolean registrarEmpleadoEnDelivery(Integer idEmpleado, Integer idDelivery) {
        String sql = "UPDATE delivery SET IDEmpleado = ? WHERE IDDelivery = ?";
        int filasActualizadas = jdbcTemplate.update(sql, idEmpleado, idDelivery);

        return filasActualizadas > 0;  // Retorna true si se actualizó al menos una fila
    }

    // Metodo para obtener los pedidos disponibles para delivery
    public List<Pedido> obtenerPedidosEmpleado() {
        String sql = "SELECT " +
                     "c.idcliente, c.nombre, c.apellido, " +
                     "d.iddireccion, d.direccion, " +
                     "p.idpedido, pg.idpago, pg.totalpago, pg.FechaPago, p.Estado as EstadoPedido, " +
                     "e.IDEmpleado, pg.fechaPago AS fechaPagoUltimo " +
                     "FROM " +
                     "cliente c " +
                     "INNER JOIN direccion d ON c.idcliente = d.idcliente " +
                     "INNER JOIN pedido p ON c.idcliente = p.idcliente " +
                     "INNER JOIN pago pg ON p.idpedido = pg.idpedido " +
                     "LEFT JOIN delivery e ON p.idpedido = e.IDPedido " +
                     "WHERE " +
                     "p.Estado = 'Pagado' AND " +
                     "e.IDEmpleado = 4 " +
                     "GROUP BY " +
                     "p.IDPedido;";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));
    }

    
}

