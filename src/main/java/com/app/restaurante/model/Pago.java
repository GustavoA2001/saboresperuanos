package com.app.restaurante.model;

public class Pago {
    private int idPago;
    private int idPedido;
    private String FechaPago;
    private double TotalPago;
    private String Titular;
    private String NumeroTarjeta;
    private String FechaVencimiento;
    private String CodigoSeguridad;

    public Pago() {}

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.FechaPago = fechaPago;
    }

    public double getTotalPago() {
        return TotalPago;
    }

    public void setTotalPago(double totalPago) {
        this.TotalPago = totalPago;
    }

    public String getTitular() {
        return Titular;
    }

    public void setTitular(String titular) {
        this.Titular = titular;
    }

    public String getNumeroTarjeta() {
        return NumeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.NumeroTarjeta = numeroTarjeta;
    }

    public String getFechaVencimiento() {
        return FechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.FechaVencimiento = fechaVencimiento;
    }

    public String getCodigoSeguridad() {
        return CodigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.CodigoSeguridad = codigoSeguridad;
    }
}
