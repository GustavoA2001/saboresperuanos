package com.app.restaurante.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigRutas implements WebMvcConfigurer {

    // Configura el interceptor para proteger las rutas
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    
    }
}
