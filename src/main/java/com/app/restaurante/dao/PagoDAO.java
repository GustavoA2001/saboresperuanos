package com.app.restaurante.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PagoDAO {
    private final JdbcTemplate jdbcTemplate;

    public PagoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Metodo para validar los datos de la tarjeta en la tabla tarjetapago
    @SuppressWarnings("deprecation")
    public boolean validarTarjeta(String numeroTarjeta, String fechaVencimiento, String codigoSeguridad, String nombreTitular) {
        String sql = "SELECT COUNT(*) FROM TarjetaPago WHERE NumeroTarjeta = ? AND FechaVencimiento = ? AND CodigoSeguridad = ? AND NombreTitular = ?";
        
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{numeroTarjeta, fechaVencimiento, codigoSeguridad, nombreTitular}, Integer.class);
        
        return count != null && count > 0; // Retorna true si se encontró al menos una coincidencia
    }

    // Metodo para registrar el pago de un pedido
    public void registrarPago(Long idPedido, double total) {
        String sql = "INSERT INTO pago (IDPedido, TotalPago, FechaPago) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, idPedido, total);
    }

    // Metodo para actualizar el estado del pedido
    public void actualizarEstadoPedido(Long idPedido, String estado) {
        String sql = "UPDATE Pedido SET Estado = ? WHERE IDPedido = ?";
        jdbcTemplate.update(sql, estado, idPedido);
    }

}
