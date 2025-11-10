package com.app.restaurante.dao;

import com.app.restaurante.model.Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class EmpleadosDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 📋 Listar todos los empleados
    public List<Empleados> listarEmpleados() {
        String sql = """
                    SELECT e.IDEmpleado,
                           CONCAT(e.Nombre, ' ', e.ApellidoPaterno, ' ', e.ApellidoMaterno) AS nombreCompleto,
                           r.NomRol AS nomRol,
                           de.Correo,
                           'Activo' AS estado
                    FROM Empleado e
                    LEFT JOIN Roles r ON e.IDRol = r.IDRol
                    LEFT JOIN DetalleEmpleado de ON e.IDEmpleado = de.IDEmpleado;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Empleados emp = new Empleados();
            emp.setIdEmpleado(rs.getInt("IDEmpleado"));
            emp.setNombre(rs.getString("nombreCompleto"));
            emp.setNomRol(rs.getString("nomRol"));
            emp.setCorreo(rs.getString("Correo"));
            emp.setEstado(rs.getString("estado"));
            return emp;
        });
    }

    // Obtener empleado por ID
    public Empleados obtenerEmpleadoPorId(int id) {
        String sql = """
                    SELECT e.IDEmpleado,
                           e.Nombre,
                           e.ApellidoPaterno,
                           e.ApellidoMaterno,
                           e.Usuario,
                           e.Contrasena,
                           d.Correo,
                           d.Telefono,
                           d.Foto,
                           r.NomRol,
                           e.IDRol
                    FROM Empleado e
                    LEFT JOIN DetalleEmpleado d ON e.IDEmpleado = d.IDEmpleado
                    LEFT JOIN Roles r ON e.IDRol = r.IDRol
                    WHERE e.IDEmpleado = ?;
                """;

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Empleados.class), id);
    }

    // Insertar nuevo empleado
    public int insertarEmpleado(Empleados e) {
        // Inserta primero el empleado
        String sqlEmpleado = """
                    INSERT INTO Empleado (Nombre, ApellidoPaterno, ApellidoMaterno, Usuario, Contrasena, IDRol)
                    VALUES (?, ?, ?, ?, ?, ?);
                """;
        int filasEmpleado = jdbcTemplate.update(sqlEmpleado,
                e.getNombre(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getUsuario(),
                e.getContrasena(),
                e.getidRol());

        // Inserta detalle (correo, teléfono, foto)
        String sqlDetalle = """
                    INSERT INTO DetalleEmpleado (Correo, Telefono, Foto, IDEmpleado)
                    VALUES (?, ?, ?, (SELECT MAX(IDEmpleado) FROM Empleado));
                """;
        jdbcTemplate.update(sqlDetalle, e.getCorreo(), e.getTelefono(), e.getFoto());

        return filasEmpleado;
    }

    // Actualizar empleado
    public int actualizarEmpleado(Empleados e) {
        String sqlEmpleado = """
                    UPDATE Empleado
                    SET Nombre = ?, ApellidoPaterno = ?, ApellidoMaterno = ?,
                        Usuario = ?, Contrasena = ?, IDRol = ?
                    WHERE IDEmpleado = ?;
                """;

        String sqlDetalle = """
                    UPDATE DetalleEmpleado
                    SET Correo = ?, Telefono = ?, Foto = ?
                    WHERE IDEmpleado = ?;
                """;

        jdbcTemplate.update(sqlDetalle, e.getCorreo(), e.getTelefono(), e.getFoto(), e.getIdEmpleado());
        return jdbcTemplate.update(sqlEmpleado,
                e.getNombre(), e.getApellidoPaterno(), e.getApellidoMaterno(),
                e.getUsuario(), e.getContrasena(), e.getidRol(), e.getIdEmpleado());
    }

    // Eliminar empleado
    public int eliminarEmpleado(int id) {
        String sqlDetalle = "DELETE FROM DetalleEmpleado WHERE IDEmpleado = ?";
        String sqlEmpleado = "DELETE FROM Empleado WHERE IDEmpleado = ?";
        jdbcTemplate.update(sqlDetalle, id);
        return jdbcTemplate.update(sqlEmpleado, id);
    }

    // Inserción completa (Empleado + DetalleEmpleado)
    public int registrarEmpleadoCompleto(Empleados e) {
        System.out.println("DAO: Iniciando inserción del empleado...");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlEmpleado = """
                    INSERT INTO Empleado (Nombre, ApellidoPaterno, ApellidoMaterno, Usuario, Contrasena, IDRol)
                    VALUES (?, ?, ?, ?, ?, ?);
                """;

        try {
            jdbcTemplate.update(connection -> {
                System.out.println("Preparando sentencia SQL para Empleado...");
                PreparedStatement ps = connection.prepareStatement(sqlEmpleado,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, e.getNombre());
                ps.setString(2, e.getApellidoPaterno());
                ps.setString(3, e.getApellidoMaterno());
                ps.setString(4, e.getUsuario());
                ps.setString(5, e.getContrasena());
                ps.setInt(6, e.getidRol());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key == null) {
                System.out.println("No se obtuvo clave generada del empleado.");
                throw new RuntimeException("No se pudo obtener el ID del nuevo empleado.");
            }

            int nuevoId = key.intValue();
            System.out.println("Clave generada: " + nuevoId);

            String sqlDetalle = """
                        INSERT INTO DetalleEmpleado (Correo, IDEmpleado)
                        VALUES (?, ?);
                    """;
            System.out.println("🟣 Insertando detalle con correo: " + e.getCorreo());
            jdbcTemplate.update(sqlDetalle, e.getCorreo(), nuevoId);

            System.out.println("Inserción completa (Empleado + DetalleEmpleado)");
            return nuevoId;

        } catch (Exception ex) {
            System.out.println("Error en registrarEmpleadoCompleto:");
            ex.printStackTrace();
            throw ex;
        }
    }

}
