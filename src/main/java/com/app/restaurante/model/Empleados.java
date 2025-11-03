package com.app.restaurante.model;

public class Empleados {
    private Integer idEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String usuario;
    private String contrasena;
    private String correo;
    private String telefono;
    private String estado;
    private String nomRol; 
    private int idRol;
    private String foto;

    // Constructor vacío
    public Empleados() {}

    // Constructor para listar
    public Empleados(Integer idEmpleado, String nombre, String nomRol, String correo, String estado) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.nomRol = nomRol;
        this.correo = correo;
        this.estado = estado;
    }

    // Getters y setters
    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNomRol() { return nomRol; }
    public void setNomRol(String nomRol) { this.nomRol = nomRol; }

    public int getidRol() { return idRol; }
    public void setidRol(int idRol) { this.idRol = idRol; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto =foto; }
}
