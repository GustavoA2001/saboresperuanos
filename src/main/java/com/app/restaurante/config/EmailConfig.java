package com.app.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public Map<String, JavaMailSenderImpl> mailSenders() {
        Map<String, JavaMailSenderImpl> map = new HashMap<>();
        map.put("gmail", crearSender("smtp.gmail.com", 587, "gustavoalegreluis@gmail.com", "xqfh xswj tkrg lwjm"));
        map.put("outlook", crearSender("smtp.office365.com", 587, "tu_correo@outlook.com", "clave_app"));
        map.put("hotmail", crearSender("smtp.office365.com", 587, "tu_correo@hotmail.com", "clave_app"));
        map.put("yahoo", crearSender("smtp.mail.yahoo.com", 587, "tu_correo@yahoo.com", "clave_app"));
        return map;
    }

    private JavaMailSenderImpl crearSender(String host, int port, String usuario, String clave) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(usuario);
        sender.setPassword(clave);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return sender;
    }
}