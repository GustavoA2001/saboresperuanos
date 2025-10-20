package com.app.restaurante.model;

import java.time.LocalDateTime;

public class CodigoRecuperacion {
    private String correo;
    private String codigo;
    private LocalDateTime fechaExpiracion;

    public CodigoRecuperacion(String correo, String codigo, LocalDateTime fechaExpiracion) {
        this.correo = correo;
        this.codigo = codigo;
        this.fechaExpiracion = fechaExpiracion;
    }

    
    public String getCorreo() {
        return correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }
}