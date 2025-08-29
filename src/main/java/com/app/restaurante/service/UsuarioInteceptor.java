package com.app.restaurante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.restaurante.model.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Interceptor que maneja la información del usuario en las peticiones HTTP
 * Implementa HandlerInterceptor para interceptar las peticiones web
 */
@Component
public class UsuarioInteceptor implements HandlerInterceptor {
   
    // Inyección de la sesión HTTP
    @Autowired
    private HttpSession session;

    /**
     * Se ejecuta después de que el controlador procese la petición
     * Verifica si hay un usuario en sesión y lo agrega al modelo
     * para que esté disponible en las vistas
     */
    @Override
    public void postHandle(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handler,
            @SuppressWarnings("null") ModelAndView modelAndView) throws Exception {

        // Verifica que exista el modelAndView y la sesión
        if (modelAndView != null && session != null) {
            
            // Obtiene el cliente de la sesión
            Cliente cliente = (Cliente) session.getAttribute("usuarios");
            
            // Si existe un cliente en sesión, lo agrega al modelo
            if (cliente != null) {
                modelAndView.addObject("cliente", cliente);
            }
        }
    }
}
