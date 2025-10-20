package com.app.restaurante.service;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.CodigoRecuperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class RecuperarService {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EncriptacionService encriptacionService;

    private Map<String, CodigoRecuperacion> codigos = new HashMap<>();

    public boolean enviarCodigo(String correo) {
        Cliente cliente = clienteDAO.findByCorreo(correo);
        if (cliente == null) {
            System.err.println("[WARN] El correo no está registrado: " + correo);
            return false;
        }

        String codigo = String.valueOf(new Random().nextInt(900000) + 100000);
        CodigoRecuperacion codigoRec = new CodigoRecuperacion(correo, codigo, LocalDateTime.now().plusMinutes(10));
        codigos.put(correo, codigoRec);

        try {
            emailService.enviarCorreo(
                    correo,
                    "Recuperación de contraseña - Sabores Peruanos",
                    "Tu código de verificación es: " + codigo + "\nEste código expira en 10 minutos.",
                    correo.split("@")[1]
            );
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Fallo al enviar correo: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarCodigo(String correo, String codigo) {
        CodigoRecuperacion cod = codigos.get(correo);
        if (cod == null || cod.isExpirado()) return false;
        return cod.getCodigo().equals(codigo);
    }

    public boolean cambiarContrasena(String correo, String nuevaContrasena) {
        Cliente cliente = clienteDAO.findByCorreo(correo);
        if (cliente == null) return false;

        String contrasenaEncriptada = encriptacionService.encriptarMD5(nuevaContrasena);

        String sql = "UPDATE cliente SET contrasena = ? WHERE correo = ?";
        jdbcTemplate.update(sql, contrasenaEncriptada, correo);
        codigos.remove(correo);
        return true;
    }
}
