package com.app.restaurante.service;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;

@Service
public class LoginService {

    @Autowired
    private ClienteDAO clienteDao;

    @Autowired
    private EncriptacionService encriptacionService;

    @Autowired
    HttpSession session;

    public Cliente validateUser(String usuario, String password)
            throws NoSuchAlgorithmException, IOException, CloneNotSupportedException {

        // Encriptar con el mismo método centralizado
        String hashedPassword = encriptacionService.encriptarMD5(password);

        // Buscar cliente con correo y contraseña encriptada
        Cliente cliente = clienteDao.findByEmailAndPassword(usuario, hashedPassword);

        // Si se encontró, guardar en sesión
        if (cliente != null) {
            session.setAttribute("cliente", cliente);
        }

        return cliente;
    }
}
