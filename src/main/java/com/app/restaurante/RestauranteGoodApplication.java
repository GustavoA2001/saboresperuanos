package com.app.restaurante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EntityScan(basePackages = "com.app.restaurante.model")
@Controller 
public class RestauranteGoodApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RestauranteGoodApplication.class, args);
    }

    // Mapea la URL raíz '/' a la página 'index.html'
    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    // Mapea la URL '/login' a la página 'login.html'
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
}

