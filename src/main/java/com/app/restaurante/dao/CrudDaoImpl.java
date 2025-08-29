package com.app.restaurante.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public abstract class CrudDaoImpl<T, ID> implements CrudDao<T, ID> {

    @Autowired // Anotación que permite acceder a la capa de datos
    protected JdbcTemplate jdbcTemplate;

    protected abstract String getTableName();
    protected abstract RowMapper<T> getRowMapper();
    /*
     * Implementamos el modelo CRUD atravez de nuestro interfaz CRUD donde solo se modelo los
     * métodos genericos
     */
    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return jdbcTemplate.query(sql, getRowMapper());
    }

    @Override
    public T findById(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        List<T> results = jdbcTemplate.query(sql, getRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void save(T entity) {
        // Implementación depende de la estructura específica de T
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(T entity) {
        // Implementación depende de la estructura específica de T
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(ID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}