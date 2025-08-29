package com.app.restaurante.model;

import java.sql.Date;
import java.math.BigDecimal;

public class Pedido {
    private Long idPedido;            
    private Long idCliente;           
    private Long idPago;              
    private String estadoPedido;      
    private Date fechaPedido;         
    private Date fechaPago;           // Fecha de pago
    private Double totalPago;         // Total pagado en el pedido
    private Double montoFinal;        // Monto final
    private Long idDireccion;         // ID de la dirección
    private String direccion;         // Dirección
    private Long idPagoUltimo;        // ID del último pago
    private BigDecimal totalPagoUltimo; // Total del último pago
    private Date fechaPagoUltimo;     // Fecha del último pago
    private String estado;            // Estado adicional del reporte
    private String nombre;            // Nombre del cliente
    private String apellido;          // Apellido del cliente

    // Getters y Setters
    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    public Double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(Double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getIdPagoUltimo() {
        return idPagoUltimo;
    }

    public void setIdPagoUltimo(Long idPagoUltimo) {
        this.idPagoUltimo = idPagoUltimo;
    }

    public BigDecimal getTotalPagoUltimo() {
        return totalPagoUltimo;
    }

    public void setTotalPagoUltimo(BigDecimal totalPagoUltimo) {
        this.totalPagoUltimo = totalPagoUltimo;
    }

    public Date getFechaPagoUltimo() {
        return fechaPagoUltimo;
    }

    public void setFechaPagoUltimo(Date fechaPagoUltimo) {
        this.fechaPagoUltimo = fechaPagoUltimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
