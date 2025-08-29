package com.app.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEmpleado")
    private Integer IDEmpleado;

    @Column(name = "Nombre", length = 50, nullable = true)
    private String nombre;

    @Column(name = "ApellidoPaterno", length = 50, nullable = true)
    private String apellidoPaterno;

    @Column(name = "ApellidoMaterno", length = 50, nullable = true)
    private String apellidoMaterno;

    @Column(name = "Usuario", length = 50, nullable = true)
    private String usuario;

    @Column(name = "Contrasena", length = 150, nullable = true)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "IDRol", nullable = true)
    private Rol rol;

    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetalleEmpleado detalleEmpleado;

    // Getters y Setters
    public DetalleEmpleado getDetalleEmpleado() {
        return detalleEmpleado;
    }

    public void setDetalleEmpleado(DetalleEmpleado detalleEmpleado) {
        this.detalleEmpleado = detalleEmpleado;
        detalleEmpleado.setEmpleado(this); // Relación bidireccional
    }


    public Integer getIdEmpleado() {
        return IDEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.IDEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //constructor vacio
    public Empleado() {
    }

    public Empleado(Integer idEmpleado, String nombre, String apellidoMaterno, String apellidoPaterno, 
    String usuario, String contrasena, Rol rol) {
        this.IDEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    
    @Override
    public String toString() {
        return "Empleado [IDEmpleado=" + IDEmpleado + ", nombre=" + nombre + ", apellidoMaterno=" + apellidoMaterno + ", " + ", usuario=" + usuario + ", contraseña=" + contrasena + ", rol=" + rol + "]";
        }
}


