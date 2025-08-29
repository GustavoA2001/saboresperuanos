package com.app.restaurante.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // Ruta para imágenes de empleados
        registry.addResourceHandler("/empleados/**")
            .addResourceLocations("classpath:/static/upload/empleados/");
        
        // Ruta para imágenes de productos
        registry.addResourceHandler("/productos/**")
                .addResourceLocations("classpath:/static/upload/productos/");
    }


}
