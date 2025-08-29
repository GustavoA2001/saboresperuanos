package com.app.restaurante.dao;

import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Direccion;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Inyección de JdbcTemplate para ejecutar consultas SQL

    // Metodo que devuelve el nombre de la tabla en la base de datos donde se almacenan los clientes
    protected String getTableName() {
        return "cliente"; // Nombre de la tabla en la base de datos
    }

    // Metodo que devuelve el RowMapper para mapear los resultados a objetos Cliente
    protected RowMapper<Cliente> getRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getLong("idCliente"));
                cliente.setNombre(rs.getString("nombre")); 
                cliente.setApellido(rs.getString("apellido"));
                cliente.setUsuario(rs.getString("usuario"));
                cliente.setContrasena(rs.getString("contrasena"));
                cliente.setCorreo(rs.getString("correo"));  
                cliente.setCorreo(rs.getString("dni"));              
                return cliente;
            }
        };
    }

    // Metodo para guardar o actualizar un cliente
    public void save(Cliente cliente) {
        if (cliente.getIdCliente() == null) {
            // Si el cliente no tiene ID, es un nuevo cliente, hacemos un INSERT
            String sql = "INSERT INTO " + getTableName() + " (nombre, apellido, usuario, correo, contrasena, dni) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getUsuario(),
                cliente.getCorreo(),
                cliente.getContrasena(),                
                cliente.getDni()
            );
        } else {
            // Si el cliente tiene ID, es un cliente existente, hacemos un UPDATE
            String sql = "UPDATE " + getTableName() + " SET nombre = ?, apellido = ?, usuario = ?, " +
                         "correo = ? WHERE idCliente = ?";
            jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getUsuario(),
                cliente.getCorreo()
            );
        }
    }

    // Metodo para obtener todos los clientes
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return jdbcTemplate.query(sql, getRowMapper());
    }

    // Metodo para verificar si existe un cliente por DNI
    public boolean existsByDni(String dni) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE dni = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        return count != null && count > 0;
    }

    // Metodo para obtener un cliente por ID
    @SuppressWarnings("deprecation")
    public Cliente findById(Long idCliente) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE idCliente = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { idCliente }, getRowMapper());
        return clientes.isEmpty() ? null : clientes.get(0); // Si no se encuentra el cliente, retornamos null
    }

    // Metodo para obtener un cliente por correo electrónico
    @SuppressWarnings("deprecation")
    public Cliente findByCorreo(String correo) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE correo = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { correo }, getRowMapper());
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    // USADO EN EL LOGIN
    // Metodo para encontrar un usuario por su correo electrónico y contraseña
    // Cambiar el nombre
    public Cliente findByEmailAndPassword(String usuario, String password) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE usuario = ? AND contrasena = ?";
        List<Cliente> results = jdbcTemplate.query(sql, getRowMapper(), usuario, password);
        return results.isEmpty() ? null : results.get(0);
    }

    // Metodo para detectar si el cliente tiene una direccion
    public Cliente findDireccionByID(Long idCliente) {
        String sql = "SELECT * FROM cliente cli " +
                     "JOIN direccion d ON cli.idCliente = d.idCliente " +
                     "WHERE cli.idCliente = ?";
        List<Cliente> results = jdbcTemplate.query(sql, getRowMapper(), idCliente);
        return results.isEmpty() ? null : results.get(0);
    }
    
    // Metodo para almacenar una direccion para el cliente
    public boolean saveDireccion(Long idCliente, String direccion, String ciudad) {
        try { 
            String sql = "INSERT INTO direccion (idCliente, direccion, ciudad) VALUES (?, ?, ?)";
            int rows = jdbcTemplate.update(sql, idCliente, direccion, ciudad);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    

    public boolean hasDireccion(Long idCliente) {
        String sql = "SELECT COUNT(*) FROM direccion WHERE idCliente = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idCliente);
        return count != null && count > 0;
    }
    

    // Metodo para obtener la lista de distritos
    public List<Direccion> findAllDistritos() {
        String sql = "SELECT IDDistrito, distrito FROM distrito"; 
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Direccion direccion = new Direccion();
            direccion.setIdDistrito(rs.getLong("IDDistrito")); 
            direccion.setDistrito(rs.getString("distrito"));
            return direccion;
        });
    }

    
    
}
