package com.app.restaurante.model;

public class Productos {

    private Long idProducto;
    private String nomProducto;
    private double PrecioUnitario;
    private String descripcion;
    private int cantidad;
    private String FechaProducto;
    private String FotoProducto;

    private Long idCategoria;


    // Constructor vacío
    public Productos() {
    }

    // Constructor con todos los atributos
    public Productos(Long idProducto, String nomProducto, double PrecioUnitario, String descripcion, 
                     int cantidad, String FechaProducto, String FotoProducto, 
                     Long idCategoria) {
        this.idProducto = idProducto;
        this.nomProducto = nomProducto;
        this.PrecioUnitario = PrecioUnitario;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.FechaProducto = FechaProducto;
        this.FotoProducto = FotoProducto;
        this.idCategoria = idCategoria;
    }

    // Constructor para el RECIBO
    public Productos(Long idProducto, String nomProducto, Double precio, int cantidad) {
        this.idProducto = idProducto;
        this.nomProducto = nomProducto;
        this.PrecioUnitario = precio;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getIdProducto() {        return idProducto;    }
    public void setIdProducto(Long idProducto) {        this.idProducto = idProducto;    }

    public String getNomProducto() {        return nomProducto;    }
    public void setNomProducto(String nomProducto) {        this.nomProducto = nomProducto;    }

    public double getPrecioUnitario() {        return PrecioUnitario;    }
    public void setPrecioUnitario(double PrecioUnitario) {        this.PrecioUnitario = PrecioUnitario;    }

    public String getDescripcion() {        return descripcion;    }
    public void setDescripcion(String descripcion) {        this.descripcion = descripcion;    }

    public int getCantidad() {        return cantidad;    }
    public void setCantidad(int cantidad) {        this.cantidad = cantidad;    }

    public String getFechaProducto() {        return FechaProducto;    }
    public void setFechaProducto(String FechaProducto) {        this.FechaProducto = FechaProducto;    }

    public String getFotoProducto() {        return FotoProducto;    }
    public void setFotoProducto(String FotoProducto) {        this.FotoProducto = FotoProducto;    }

    public Long getIdCategoria() {        return idCategoria;    }
    public void setIdCategoria(Long idCategoria) {        this.idCategoria = idCategoria;    }

    

}
