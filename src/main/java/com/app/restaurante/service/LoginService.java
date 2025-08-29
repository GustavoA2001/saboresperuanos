package com.app.restaurante.service;

import jakarta.servlet.http.HttpSession;
// Importaciones para manejar excepciones y seguridad
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
// Importación de anotación de Spring Framework para servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.utils.Validation;
// Definición del servicio como un componente de Spring
@Service
public class LoginService {
    // Inyección de dependencia del DAO de Usuarios
    @Autowired
    private ClienteDAO clienteDao;
    // Inyección de dependencia de la sesión HTTP
    @Autowired
    HttpSession session;
    // Método para validar y autenticar un usuario por correo electrónico y
    // contraseña
    public Cliente validateUser(String usuario, String password)
            throws NoSuchAlgorithmException, IOException, CloneNotSupportedException {
        // Hashing de la contraseña utilizando MD5
        String hashedPassword = Validation.md5(password);
        // Buscar al usuario por correo electrónico y contraseña hasheada en la base de datos
        Cliente cliente = clienteDao.findByEmailAndPassword(usuario, hashedPassword);
        // Si se encuentra al usuario, se guarda en la sesión HTTP
        if (cliente != null) {
            session.setAttribute("cliente", cliente);
        }
        // Devuelve el objeto Usuarios encontrado (o null si no se encontró)
        return cliente;
    }
}