package com.app.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.restaurante.model.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Empleado findTopByOrderByIDEmpleadoDesc();

    @Query("SELECT e FROM Empleado e WHERE e.usuario = :usuario AND e.contrasena = :contrasena")
    Empleado findByUsuarioAndContrasena(@Param("usuario") String usuario, 
                                        @Param("contrasena") String contrasena);

}
