package com.app.restaurante.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Direccion;
import com.app.restaurante.model.Pedido;
import com.app.restaurante.model.Productos;
import com.app.restaurante.utils.Validation;

@Repository
public class CuentaDAO {
    
    private final JdbcTemplate jdbcTemplate;

    public CuentaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Metodo para obtener direcciones por ID de cliente
     */
    @SuppressWarnings("deprecation")
    public List<Direccion> obtenerDireccionesPorCliente(Long idCliente) {
        
        String sql = "SELECT dir.idDireccion, dir.idCliente, dir.Direccion, dir.Referencia, di.distrito " +
                     "FROM direccion dir " +
                     "INNER JOIN Distrito di ON dir.IDDistrito = di.IDDistrito WHERE idCliente = ?;";
        List<Direccion> direcciones = jdbcTemplate.query(sql, new Object[]{idCliente}, new BeanPropertyRowMapper<>(Direccion.class));
    
        // Verifica el contenido de las direcciones antes de retornarlas
        //for (Direccion direccion : direcciones) {
        //    System.out.println("Direccion: " + direccion.getDireccion());
        //}    

        return direcciones;
    }

    /**
     * Metodo para obtener los pedidos por ID de cliente
     */
    @SuppressWarnings("deprecation")
    public List<Pedido> obtenerPedidosPorCliente(Long idCliente) {

        String sql = "SELECT ped.IDPedido, ped.IDCliente, pa.IDPago, ped.Estado, ped.FechaPedido, " +
                     "pa.FechaPago, pa.TotalPago, ped.MontoFinal " +
                     "FROM pedido ped " +
                     "INNER JOIN pago pa ON ped.IDPedido = pa.IDPedido " +
                     "WHERE idCliente = ? and ped.estado='Pagado';";
        List<Pedido> pedidos = jdbcTemplate.query(sql, new Object[]{idCliente}, new BeanPropertyRowMapper<>(Pedido.class));

        return pedidos;
    }

    // Metodo para obtener un cliente por ID
    @SuppressWarnings("deprecation")
    public Cliente findById(Long idCliente) {
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { idCliente }, new BeanPropertyRowMapper<>(Cliente.class));
        
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    // Metodo para actualizar los datos del cliente
    public void update(Cliente cliente) {
        
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, usuario = ?, correo = ? WHERE idCliente = ?";
        jdbcTemplate.update(sql,
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getUsuario(),
            cliente.getCorreo(),
            cliente.getIdCliente()
        );
        
    }
}
