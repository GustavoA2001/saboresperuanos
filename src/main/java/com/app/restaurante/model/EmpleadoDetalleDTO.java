package com.app.restaurante.model;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.model.DetalleEmpleado;

public class EmpleadoDetalleDTO {
    private Empleado empleado;
    private DetalleEmpleado detalleEmpleado;

    // Getters y setters
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public DetalleEmpleado getDetalleEmpleado() {
        return detalleEmpleado;
    }

    public void setDetalleEmpleado(DetalleEmpleado detalleEmpleado) {
        this.detalleEmpleado = detalleEmpleado;
    }
}

