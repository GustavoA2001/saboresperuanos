package com.app.restaurante.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.restaurante.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

}
