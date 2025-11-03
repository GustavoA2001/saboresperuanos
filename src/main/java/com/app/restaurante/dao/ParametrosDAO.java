package com.app.restaurante.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ParametrosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ParametrosDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // === ROLES ===
    public List<Map<String, Object>> obtenerRoles() {
        String sql = "SELECT IDRol, NomRol, Descripcion, EstadoRoles FROM Roles";
        return jdbcTemplate.queryForList(sql);
    }

    // === CATEGORÍAS ===
    public List<Map<String, Object>> obtenerCategorias() {
        String sql = "SELECT IDCategoria, NomCategoria, Descripcion, EstadoCategoria FROM CategoriaProducto";
        return jdbcTemplate.queryForList(sql);
    }

    // === DISTRITOS ===
    public List<Map<String, Object>> obtenerDistritos() {
        String sql = "SELECT IDDistrito, Distrito, EstadoDistrito FROM Distrito";
        return jdbcTemplate.queryForList(sql);
    }
}
