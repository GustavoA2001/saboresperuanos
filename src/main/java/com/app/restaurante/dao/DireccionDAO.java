package com.app.restaurante.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.restaurante.model.Direccion;

@Repository
public class DireccionDAO {
    
    
    // Inyeccion para ejecutar consultas SQL
    private final JdbcTemplate jdbcTemplate;

    // Metodo que devuelve el nombre de la tabla en la base de datos donde se almacenan las direcciones
    protected String getTableName() {
        return "direccion"; // Nombre de la tabla en la base de datos
    }

    // Metodo que devuelve el nombre de la tabla en la base de datos donde se almacenan los distritos
    protected String getDistritosTableName() {
        return "distritos"; // Nombre de la tabla en la base de datos
    }

    /**
     * Constructor que recibe el JdbcTemplate
     */
    public DireccionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
    -------------------------------------- 
    */

    @SuppressWarnings("deprecation")
    public Direccion findDireccionByCliente(Long idCliente) {
        String sql = "SELECT d.idDireccion, d.idCliente, d.direccion, d.referencia, d.idDistrito, d.distrito " +
                     "FROM direccion d WHERE d.idCliente = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idCliente}, (rs, rowNum) -> {
            Direccion direccion = new Direccion();
            direccion.setIdDireccion(rs.getLong("idDireccion"));
            direccion.setIdCliente(rs.getLong("idCliente"));
            direccion.setDireccion(rs.getString("direccion"));
            direccion.setReferencia(rs.getString("referencia"));
            direccion.setIdDistrito(rs.getLong("idDistrito"));
            direccion.setDistrito(rs.getString("distrito"));
            return direccion;
        });
    }
    
    /* */      
    public void saveDireccion(Direccion direccion) {
        String sql = "INSERT INTO Direccion (Direccion, Referencia, idDistrito, idCliente) " +
                     "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            direccion.getDireccion(),
            direccion.getReferencia(),
            direccion.getIdDistrito(),
            direccion.getIdCliente()
        );       
    }

    /*
    ---------------------------------------
     */


    /*
     * Metodo para obtener todos los datos de la tabla dirección
     */
    public List<Direccion> findAllDirecciones() {
        // Consulta SQL para obtener todos los datos de la tabla dirección
        String sql = "SELECT iddireccion, idcliente, direccion, referencia, iddistrito FROM " + getTableName();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Direccion.class));
    }

    /*
     * Metodo para obtener todos los datos de la tabla distritos
     */
    public List<Direccion> findAllDistritos() {
        // Consulta SQL para obtener todos los datos de la tabla distritos
        String sql = "SELECT idDistrito, distrito FROM " + getDistritosTableName();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Direccion.class));
    }

}
