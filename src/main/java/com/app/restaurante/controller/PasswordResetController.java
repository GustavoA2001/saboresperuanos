package com.app.restaurante.controller;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private JavaMailSender mailSender;

    // 🔹 1. Recibe el correo desde el formulario
    @PostMapping("/recuperar-contrasena")
    public ModelAndView processForgotPassword(@RequestParam("emailRecuperacion") String correo) {
        ModelAndView mv = new ModelAndView("login"); // vuelve al login

        Cliente cliente = clienteDAO.findByCorreo(correo);

        if (cliente == null) {
            mv.addObject("mensajeError", "No existe una cuenta con ese correo electrónico.");
            return mv;
        }

        // 🔹 2. Generar token aleatorio (no se guarda, solo se envía en el enlace)
        String token = UUID.randomUUID().toString();

        // 🔹 3. Crear enlace de restablecimiento
        String resetLink = "http://localhost:8087/restablecer-contrasena?token=" + token + "&email=" + correo;

        // 🔹 4. Enviar el correo
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Recuperación de contraseña - Restaurante");
        mensaje.setText("Hola " + cliente.getNombre() + ",\n\n" +
                "Has solicitado restablecer tu contraseña.\n" +
                "Haz clic en el siguiente enlace para cambiarla:\n" + resetLink +
                "\n\nSi no fuiste tú, ignora este mensaje.");

        mailSender.send(mensaje);

        mv.addObject("mensajeExito", "Se envió un enlace de recuperación a tu correo.");
        return mv;
    }

    // 🔹 5. Mostrar formulario para nueva contraseña
    @GetMapping("/restablecer-contrasena")
    public ModelAndView showResetForm(
            @RequestParam("token") String token,
            @RequestParam("email") String correo) {

        ModelAndView mv = new ModelAndView("restablecer_contrasena");
        mv.addObject("token", token);
        mv.addObject("correo", correo);
        return mv;
    }

    @PostMapping("/actualizar-contrasena")
    public ModelAndView updatePassword(
            @RequestParam("correo") String correo,
            @RequestParam("nuevaContrasena") String nuevaContrasena) {

        ModelAndView mv = new ModelAndView("login");

        // Buscar el cliente por correo
        Cliente cliente = clienteDAO.findByCorreo(correo);
        if (cliente != null) {
            // Encriptar la nueva contraseña
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(nuevaContrasena);

            // Actualizar contraseña en la BD (encriptada)
            clienteDAO.actualizarContrasenaPorCorreo(correo, hash);

            mv.addObject("mensajeExito", "Tu contraseña ha sido actualizada correctamente.");
        } else {
            mv.addObject("mensajeError", "No se pudo actualizar la contraseña.");
        }

        return mv;
    }

}
