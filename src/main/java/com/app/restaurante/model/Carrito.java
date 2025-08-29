package com.app.restaurante.model;

public class Carrito {
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double totalProducto;
    private int idCarrito;
    private int idProducto;
    private int idPedido;
    private double totalCarrito;
    private String clienteNombre;
    private String clienteApellido;

    public Carrito(){   };

    public Carrito(String producto, int cantidad, double precioUnitario, double totalProducto, 
                  int idCarrito, int idProducto, double totalCarrito, 
                  String clienteNombre, String clienteApellido) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalProducto = totalProducto;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.totalCarrito = totalCarrito;
        this.clienteNombre = clienteNombre;
        this.clienteApellido = clienteApellido;
    }


    public String getProducto() {        return producto;    }
    public void setProducto(String producto) {        this.producto = producto;    }

    public int getCantidad() {        return cantidad;    }
    public void setCantidad(int cantidad) {        this.cantidad = cantidad;    }

    public double getPrecioUnitario() {        return precioUnitario;    }
    public void setPrecioUnitario(double precioUnitario) {        this.precioUnitario = precioUnitario;    }

    public double getTotalProducto() {        return totalProducto;    }
    public void setTotalProducto(double totalProducto) {        this.totalProducto = totalProducto;    }

    public int getIdCarrito() {        return idCarrito;    }
    public void setIdCarrito(int idCarrito) {        this.idCarrito = idCarrito;    }

    public int getIdProducto() {        return idProducto;    }
    public void setIdProducto(int idProducto) {        this.idProducto = idProducto;    }

    public int getIdPedido() {        return idPedido;    }
    public void setIdPedido(int idPedido) {        this.idPedido = idPedido;    }

    public double getTotalCarrito() {        return totalCarrito;    }
    public void setTotalCarrito(double totalCarrito) {        this.totalCarrito = totalCarrito;    }

    public String getClienteNombre() {        return clienteNombre;    }
    public void setClienteNombre(String clienteNombre) {        this.clienteNombre = clienteNombre;    }

    public String getClienteApellido() {        return clienteApellido;    }
    public void setClienteApellido(String clienteApellido) {        this.clienteApellido = clienteApellido;    }

    
}
