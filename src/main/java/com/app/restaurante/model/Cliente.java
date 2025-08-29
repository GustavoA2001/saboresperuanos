package com.app.restaurante.model;

import java.util.Optional;

public class Cliente {

    // Atributos en la tabla cliente
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private String usuario;
    private String contrasena;
    // Atributos en la tabla detallecliente
    private String telefono;
    private Optional<String> fotoCliente;
    

    // Constructor vacío
    public Cliente() {}

    // Constructor con todos los atributos
    public Cliente(Long idCliente, String nombre, String apellido, String dni, String correo, String usuario, String contrasena, String telefono, String fotoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.fotoCliente = Optional.ofNullable(fotoCliente);
    }

    public Cliente(Long idCliente, String usuario, String contrasena) {
        this.idCliente = idCliente;
        //this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }


    // Getters y Setters

    public Long getIdCliente() {        return idCliente;    }
    public void setIdCliente(Long idCliente) {        this.idCliente = idCliente;    }

    public String getNombre() {        return nombre;    }
    public void setNombre(String nombre) {        this.nombre = nombre;    }

    public String getApellido() {        return apellido;    }
    public void setApellido(String apellido) {        this.apellido = apellido;    }

    public String getDni() {        return dni;    }
    public void setDni(String dni) {        this.dni = dni;    }

    public String getCorreo() {        return correo;    }
    public void setCorreo(String correo) {        this.correo = correo;    }

    public String getUsuario(){        return usuario;    }
    public void setUsuario(String usuario){        this.usuario = usuario;    }

    public String getContrasena() {        return contrasena;    }
    public void setContrasena(String contrasena) {        this.contrasena = contrasena;    }

    public String getTelefono() {        return telefono;    }
    public void setTelefono(String telefono) {        this.telefono = telefono;    }

    public Optional<String> getFotoCliente() {
        return fotoCliente;
    }

    public void setFotoCliente(String fotoCliente) {
        this.fotoCliente = Optional.ofNullable(fotoCliente);  // Convertir a Optional
    }

}
