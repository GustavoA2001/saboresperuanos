package com.app.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalleempleado")
public class DetalleEmpleado {
    
    @Id
    @Column(name = "IDDetalleEmp")
    private Integer idDetalleEmp;

    @Column(name = "Telefono", length = 9, nullable = true)
    private String telefono;

    @Column(name = "Correo", length = 100, nullable = true)
    private String correo;

    @Column(name = "Foto", length = 150, nullable = true)
    private String foto;
    
    @OneToOne
    @MapsId // Indica que este ID está vinculado al ID de 'Empleado'
    @JoinColumn(name = "IDEmpleado", nullable = true)
    private Empleado empleado;

    public Integer getIdDetalleEmp() {
        return idDetalleEmp;
    }

    public void setIdDetalleEmp(Integer idDetalleEmp) {
        this.idDetalleEmp = idDetalleEmp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public DetalleEmpleado() {
    }

    public DetalleEmpleado(Integer idDetalleEmp, String telefono, String correo, String foto, Empleado empleado) {
        this.idDetalleEmp = idDetalleEmp;
        this.telefono = telefono;
        this.correo = correo;
        this.foto = foto;
        this.empleado = empleado;
    }

    // Getters y Setters
    @Override 
    public String toString() {
        return "DetalleEmpleado [idDetalleEmp=" + idDetalleEmp + ", telefono=" + telefono + ", correo=" + correo + ", foto=" + foto + ", empleado=" + empleado + "] ";
        }
    
}
