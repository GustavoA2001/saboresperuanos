package com.app.restaurante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.restaurante.model.Cliente;
import com.app.restaurante.dao.ClienteDAO;

@Service
public class LoginService {

    @Autowired
    private ClienteDAO clienteDAO;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Cliente validateUser(String usuario, String password) {
        Cliente cliente = clienteDAO.findByUsuario(usuario);

        if (cliente != null && passwordEncoder.matches(password, cliente.getContrasena())) {
            System.out.println("✅ Contraseña válida para: " + usuario);
            return cliente;
        }

        System.out.println("❌ Contraseña inválida o usuario no encontrado");
        return null;
    }
}
