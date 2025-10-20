package com.app.restaurante.service;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.dao.PasswordResetTokenDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private PasswordResetTokenDAO tokenDAO;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Paso 1: Enviar correo con enlace
    public void enviarToken(String email) {
        Cliente cliente = clienteDAO.findByCorreo(email);
        if (cliente == null) return; // No existe correo en la BD

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusHours(1);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpirationDate(expiracion);

        tokenDAO.save(resetToken);

        String enlace = "http://localhost:8087/password/restablecer?token=" + token;
        String mensaje = "Hola " + cliente.getNombre() + ",\n\n" +
                        "Has solicitado restablecer tu contraseña.\n" +
                        "Haz clic en el siguiente enlace (válido por 1 hora):\n" + enlace;

        emailService.enviarCorreo(email, "Recuperación de contraseña", mensaje);
    }

    // Paso 2: Restablecer contraseña
    public boolean restablecerContraseña(String token, String nuevaContrasena) {
        PasswordResetToken resetToken = tokenDAO.findByToken(token);
        if (resetToken == null || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Cliente cliente = clienteDAO.findByCorreo(resetToken.getEmail());
        if (cliente == null) return false;

        // Encriptar y actualizar la contraseña
        String hashedPassword = passwordEncoder.encode(nuevaContrasena);
        clienteDAO.actualizarContrasenaPorCorreo(resetToken.getEmail(), hashedPassword);
        
        tokenDAO.delete(token); // Eliminar token usado
        return true;
    }
}
