package com.app.restaurante.service;

import com.app.restaurante.model.DetalleEmpleado;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface DetEmpleadoService {
    List<DetalleEmpleado> listarDetEmpleado();
    DetalleEmpleado obtenerDetEmpleadoPorId(Integer id);
    DetalleEmpleado guardarDetEmpleado (DetalleEmpleado detEmpleado, MultipartFile file) throws IOException;;
    DetalleEmpleado actualizarDetEmpleado(DetalleEmpleado detEmpleado, MultipartFile file) throws IOException;;
    void eliminarDetEmpleado (Integer id) throws IOException;


}
