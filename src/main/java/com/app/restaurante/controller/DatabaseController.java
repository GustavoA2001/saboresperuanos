package com.app.restaurante.controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    // Metodo para realizar la prueba de la conexion a la BD
    @GetMapping("/validation")
    public String validateDatabaseConnection(Model model) {
        String message;
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                message = "¡Conectado a la base de datos exitosamente!";
            } else {
                message = "No se pudo conectar a la base de datos.";
            }
        } catch (SQLException e) {
            message = "No se pudo conectar a la base de datos:" + e.getMessage();
        }
        model.addAttribute("message", message);
        return "validation";
    }
}