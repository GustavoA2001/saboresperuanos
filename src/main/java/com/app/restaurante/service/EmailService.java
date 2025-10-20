package com.app.restaurante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private Map<String, JavaMailSenderImpl> mailSenders;

    public void enviarCorreo(String destinatario, String asunto, String mensaje, String dominio) {
        JavaMailSenderImpl sender = seleccionarProveedor(dominio);

        if (sender == null) {
            System.err.println("[ERROR] No hay configuración para el dominio: " + dominio);
            System.err.println("[ERROR] No hay configuración para el dominio: " + dominio);
            System.err.println("[ERROR] No hay configuración para el dominio: " + dominio);

            return;
        }

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(destinatario);
            mail.setSubject(asunto);
            mail.setText(mensaje);
            mail.setFrom(sender.getUsername());

            sender.send(mail);
            System.out.println("[SUCCESS] Correo enviado correctamente a " + destinatario);
            System.out.println("[SUCCESS] Correo enviado correctamente a " + destinatario);
            System.out.println("[SUCCESS] Correo enviado correctamente a " + destinatario);

        } catch (Exception e) {
            System.err.println("[ERROR] Error al enviar correo: " + e.getMessage());
            System.err.println("[ERROR] Error al enviar correo: " + e.getMessage());
            System.err.println("[ERROR] Error al enviar correo: " + e.getMessage());

            e.printStackTrace();
        }
    }

    private JavaMailSenderImpl seleccionarProveedor(String dominio) {
        dominio = dominio.toLowerCase();
        if (dominio.contains("gmail")) return mailSenders.get("gmail");
        if (dominio.contains("outlook")) return mailSenders.get("outlook");
        if (dominio.contains("hotmail")) return mailSenders.get("hotmail");
        if (dominio.contains("yahoo")) return mailSenders.get("yahoo");
        System.out.println("[WARN] Proveedor no reconocido, se usará Gmail por defecto");
        System.out.println("[WARN] Proveedor no reconocido, se usará Gmail por defecto");
        System.out.println("[WARN] Proveedor no reconocido, se usará Gmail por defecto");

        return mailSenders.get("gmail");
    }
}