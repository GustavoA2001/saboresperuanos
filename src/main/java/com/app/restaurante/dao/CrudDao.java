package com.app.restaurante.dao;

import java.util.List;

// Clase Generica para el CRUD
public interface CrudDao<T, ID> {
    //Métodos generales que seran heredadas
    List<T> findAll(); // Llenar Array
    T findById(ID id); // Buscar por ID
    void save(T entity); // Guardar 
    void update(T entity); // Actualizar 
    void deleteById(ID id); // Eliminar por ID
}