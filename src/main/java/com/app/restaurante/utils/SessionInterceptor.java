package com.app.restaurante.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;  // Cambiar javax a jakarta
import jakarta.servlet.http.HttpServletResponse; // Cambiar javax a jakarta

public class SessionInterceptor implements HandlerInterceptor {

    // Este método se ejecutará antes de que la solicitud llegue al controlador
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }
}
