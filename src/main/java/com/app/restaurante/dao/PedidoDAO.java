package com.app.restaurante.dao;

import com.app.restaurante.model.Pedido;
//import com.app.restaurante.model.PedidoReporte;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * Clase DAO para gestionar las operaciones de base de datos relacionadas con los pedidos
 */
@Repository
public class PedidoDAO {

    private final JdbcTemplate jdbcTemplate;

    public PedidoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected String getTableName() {
        return "pedido";
    }

    /*
     * RELACIONADO CON EL ADMINITRADOR EMPLEADO
     */
    /*
     * Metodo para obtener todos los pedidos
    */

    public List<Pedido> obtenerPedidosPagados() {
        String sql = "SELECT c.idcliente, c.nombre, c.apellido, "+
                        "d.iddireccion, d.direccion, " +
                        "p.idpedido, " +
                        "MAX(pg.idpago) AS idPagoUltimo, " +
                        "MAX(pg.totalpago) AS totalPagoUltimo, " +
                        "MAX(pg.FechaPago) AS fechaPagoUltimo, " +
                        "p.Estado " +
                        "FROM cliente c " +
                        "INNER JOIN direccion d ON c.idcliente = d.idcliente " +
                        "INNER JOIN pedido p ON c.idcliente = p.idcliente " +
                        "INNER JOIN pago pg ON p.idpedido = pg.idpedido " +
                        "WHERE p.Estado = 'Pagado' " +
                        "GROUP BY c.idcliente, c.nombre, c.apellido, " +
                        "d.iddireccion, d.direccion, p.idpedido, p.Estado;";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));
    }

    /**
     * Obtiene todos los pedidos de la base de datos
     * @return Lista de todos los pedidos
     */
    public List<Pedido> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));
    }

    /**
     * Busca un pedido específico por su ID
     * @param id ID del pedido a buscar
     * @return Pedido encontrado o null si no existe
     */
    public Pedido findById(Long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE IDPedido = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pedido.class), id);
    }
}
