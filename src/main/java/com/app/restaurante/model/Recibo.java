package com.app.restaurante.model;

import java.time.LocalDateTime;
import java.util.List;

public class Recibo {
    private int idCliente;
    private String nombre;
    private String apellido;
    private int idDireccion;
    private String direccion;
    private int idPedido;
    private String productosEnCarrito;
    private List<Productos> productos;
    private int idPago;
    private double totalPago;
    private LocalDateTime fechaPago;  // Cambio aquí a LocalDateTime

    public Recibo() {}
    
    // Constructor, getters y setters
    public Recibo(int idCliente, String nombre, String apellido, int idDireccion, String direccion,
                    int idPedido, String productosEnCarrito, List<Productos> productos, int idPago, double totalPago) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idDireccion = idDireccion;
        this.direccion = direccion;
        this.idPedido = idPedido;
        this.productosEnCarrito = productosEnCarrito;
        this.productos = productos;
        this.idPago = idPago;
        this.totalPago = totalPago;
    }

    // Getters y setters para todos los atributos
    public int getIdCliente() {        return idCliente;    }
    public void setIdCliente(int idCliente) {        this.idCliente = idCliente;    }

    public String getNombre() {        return nombre;    }
    public void setNombre(String nombre) {        this.nombre = nombre;    }

    public String getApellido() {        return apellido;    }
    public void setApellido(String apellido) {        this.apellido = apellido;    }

    public int getIdDireccion() {        return idDireccion;    }
    public void setIdDireccion(int idDireccion) {        this.idDireccion = idDireccion;    }

    public String getDireccion() {        return direccion;    }
    public void setDireccion(String direccion) {        this.direccion = direccion;    }

    public int getIdPedido() {        return idPedido;    }
    public void setIdPedido(int idPedido) {        this.idPedido = idPedido;    }

    public String getProductosEnCarrito() {        return productosEnCarrito;    }
    public void setProductosEnCarrito(String productosEnCarrito) {        this.productosEnCarrito = productosEnCarrito;    }

    public List<Productos> getProductos() {        return productos;    }
    public void setProductos(List<Productos> productos) {        this.productos = productos;    }

    public int getIdPago() {        return idPago;    }
    public void setIdPago(int idPago) {        this.idPago = idPago;    }

    public double getTotalPago() {        return totalPago;    }
    public void setTotalPago(double totalPago) {        this.totalPago = totalPago;    }
    
    public LocalDateTime getFechaPago() { return fechaPago; }  // Getter para LocalDateTime
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }  // Setter para LocalDateTime

}
