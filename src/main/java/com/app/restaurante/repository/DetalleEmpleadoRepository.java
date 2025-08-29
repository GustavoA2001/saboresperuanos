package com.app.restaurante.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.restaurante.model.DetalleEmpleado;

public interface DetalleEmpleadoRepository extends JpaRepository<DetalleEmpleado, Integer> {

}
