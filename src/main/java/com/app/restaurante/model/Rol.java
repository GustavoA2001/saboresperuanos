package com.app.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRol")
    private Integer idRol;

    @Column(name = "NomRol", length = 50, nullable = true)
    private String nomRol;

    @Column(name = "Descripcion", length = 100, nullable = true)
    private String descripcion;

    //getters y setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNomRol() {
        return nomRol;
    }

    public void setNomRol(String nomRol) {
        this.nomRol = nomRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores
    
    public Rol() {
    }

    // Constructor con parámetros
    public Rol(Integer idRol, String nomRol, String descripcion) {
        this.idRol = idRol;
        this.nomRol = nomRol;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Rol [idRol=" + idRol + ", nomRol=" + nomRol + ", descripcion=" + descripcion +  "]";
    }
}

