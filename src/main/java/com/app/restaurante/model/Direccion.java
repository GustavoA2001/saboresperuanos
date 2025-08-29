package com.app.restaurante.model;

//La clase sera para DIRECCION y DISTRITO
public class Direccion {

    private Long idDireccion;
    private Long idCliente;
    private String Direccion;
    private String Referencia;

    private Long idDistrito;
    private String Distrito;


    public Direccion() {}

    public Direccion(Long idDireccion, Long idDistrito, Long idCliente, String Direccion, String Referencia) {
        this.idDireccion = idDireccion;
        this.idDistrito = idDistrito;
        this.idCliente = idCliente;
        this.Direccion = Direccion;
        this.Referencia = Referencia;
    }

    // Getters y Setters
    public Long getIdDireccion() {        return idDireccion;    }
    public void setIdDireccion(Long idDireccion) {        this.idDireccion = idDireccion;    }

    public Long getIdDistrito() {        return idDistrito;    }
    public void setIdDistrito(Long idDistrito) {        this.idDistrito = idDistrito;    }

    public Long getIdCliente() {        return idCliente;    }
    public void setIdCliente(Long idCliente) {        this.idCliente = idCliente;    }

    public String getDireccion() {        return Direccion;    }
    public void setDireccion(String Direccion) {        this.Direccion = Direccion;    }

    public String getReferencia() {        return Referencia;    }
    public void setReferencia(String Referencia) {        this.Referencia = Referencia;    }

    public String getDistrito() {        return Distrito;    }
    public void setDistrito(String Distrito){        this.Distrito = Distrito;    }
    
}
