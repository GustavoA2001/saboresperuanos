package com.app.restaurante.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckoutDAO {

    public CheckoutDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener direcciones del cliente
    public List<Map<String, Object>> obtenerDireccionesPorCliente(Long idCliente) {
        String sql = """
                    SELECT d.IDDireccion, d.DireccionCompleta, di.Distrito
                    FROM direccion d
                    INNER JOIN distrito di ON d.IDDistrito = di.IDDistrito
                    WHERE d.IDCliente = ?
                """;
        return jdbcTemplate.queryForList(sql, idCliente);
    }

    // Agregar una nueva dirección
    public void agregarDireccion(Long idCliente, String direccionCompleta, String referencia, int idDistrito) {
        String sql = "INSERT INTO direccion (DireccionCompleta, Referencia, IDCliente, IDDistrito) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, direccionCompleta, referencia, idCliente, idDistrito);
    }

    // Asignar una dirección al pedido actual
    public void asignarDireccionPedido(int idPedido, int idDireccion) {
        String sql = "UPDATE pedido SET IDDireccion = ? WHERE IDPedido = ?";
        jdbcTemplate.update(sql, idDireccion, idPedido);
    }

    // Actualizar estado y pago del pedido
    public void actualizarEstadoPedido(int idPedido, String estado, String metodoPago) {
        String sql = "UPDATE pedido SET EstadoPedido = ?, FechaPedido = NOW() WHERE IDPedido = ?";
        jdbcTemplate.update(sql, estado, idPedido);
        // métodoPago se podría almacenar en tabla 'pago'
    }

    // Listar todos los distritos
    public List<Map<String, Object>> obtenerDistritos() {
        String sql = "SELECT IDDistrito, Distrito FROM distrito ORDER BY Distrito";
        return jdbcTemplate.queryForList(sql);
    }

    public void asignarDireccionAPedido(int idPedido, int idDireccion) {
        String sql = "UPDATE pedido SET IDDireccion = ? WHERE IDPedido = ?";
        jdbcTemplate.update(sql, idDireccion, idPedido);
    }

    // Obtener una dirección específica
    public Map<String, Object> obtenerDireccionPorId(Integer idDireccion) {
        String sql = """
                    SELECT d.IDDireccion, d.DireccionCompleta, d.Referencia, di.Distrito
                    FROM direccion d
                    INNER JOIN distrito di ON d.IDDistrito = di.IDDistrito
                    WHERE d.IDDireccion = ?
                """;
        return jdbcTemplate.queryForMap(sql, idDireccion);
    }

    // Obtener la última dirección agregada de un cliente
    public Map<String, Object> obtenerUltimaDireccion(Long idCliente) {
        String sql = """
                    SELECT d.IDDireccion, d.DireccionCompleta, d.Referencia, di.Distrito
                    FROM direccion d
                    INNER JOIN distrito di ON d.IDDistrito = di.IDDistrito
                    WHERE d.IDCliente = ?
                    ORDER BY d.IDDireccion DESC
                    LIMIT 1
                """;
        return jdbcTemplate.queryForMap(sql, idCliente);
    }

    public void registrarPago(int idPedido, int idTarjeta, double total) {
        String sql = "INSERT INTO pago (IDPedido, IDTarjeta, PagoTotal, FechaPago) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(sql, idPedido, idTarjeta, total);
    }

    public List<Map<String, Object>> obtenerDatosPedido(int idPedido) {
        System.out.println("[CheckoutDAO] Ejecutando obtenerDatosPedido() para IDPedido = " + idPedido);

        String sql = """
                    SELECT
                        p.IDPedido,
                        p.FechaPedido,
                        p.EstadoPedido,
                        p.MontoFinal,
                        c.Nombre,
                        c.Apellido,
                        c.Correo,
                        d.DireccionCompleta,
                        pa.PagoTotal,
                        pa.FechaPago,
                        del.CostoDelivery
                    FROM pedido p
                    INNER JOIN cliente c ON p.IDCliente = c.IDCliente
                    LEFT JOIN direccion d ON p.IDDireccion = d.IDDireccion
                    LEFT JOIN pago pa ON pa.IDPedido = p.IDPedido
                    LEFT JOIN delivery del ON del.IDPedido = p.IDPedido
                    WHERE p.IDPedido = ?
                """;

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.queryForList(sql, idPedido);
            System.out.println("[CheckoutDAO] Filas encontradas: " + resultados.size());

            if (resultados.isEmpty()) {
                System.out.println("[CheckoutDAO] No se encontraron datos para el pedido #" + idPedido);
            } else {
                System.out.println("[CheckoutDAO] Pedido encontrado: " + resultados.get(0));
            }

            return resultados;
        } catch (Exception e) {
            System.err.println("[CheckoutDAO] Error en obtenerDatosPedido(): " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    // =====================================================
    // OBTENER PRODUCTOS DEL PEDIDO (JOIN con carrito)
    // =====================================================
    public List<Map<String, Object>> obtenerProductosPedido(int idPedido) {
        System.out.println("[CheckoutDAO] Ejecutando obtenerProductosPedido() para IDPedido = " + idPedido);

        String sql = """
                    SELECT
                        ph.NomProducto,
                        ph.Descripcion,
                        c.Cantidad,
                        c.PrecioProducto,
                        (c.Cantidad * c.PrecioProducto) AS Subtotal
                    FROM carrito c
                    INNER JOIN producto p ON c.IDProducto = p.IDProducto
                    INNER JOIN productohistoria ph ON p.IDProdHistoria = ph.IDProdHistoria
                    WHERE c.IDPedido = ?
                """;

        try {
            List<Map<String, Object>> productos = jdbcTemplate.queryForList(sql, idPedido);
            System.out.println("[CheckoutDAO] Productos encontrados: " + productos.size());

            if (productos.isEmpty()) {
                System.out.println("[CheckoutDAO] No hay productos asociados al pedido #" + idPedido);
            }

            return productos;
        } catch (Exception e) {
            System.err.println("[CheckoutDAO] Error en obtenerProductosPedido(): " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    // Crear registro de delivery al confirmar pago
    public void crearDeliveryParaPedido(int idPedido, double costoEnvio) {
        String sql = """
                    INSERT INTO delivery (IDPedido, CostoDelivery, Estado, FechaDelivery)
                    VALUES (?, ?, 'Pendiente', NOW())
                """;
        jdbcTemplate.update(sql, idPedido, costoEnvio);

    }

    // Validar si una tarjeta existe en la base de datos y devolver su ID
    public Integer validarTarjeta(String numeroTarjeta, String nombreTitular, String fechaExp, String cvv) {
        String sql = """
                SELECT IDTarjeta
                FROM tarjetapago
                WHERE NumeroTarjeta = ? AND NombreTitular = ? AND FechaVencimiento = ? AND CodigoSeguridad = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, numeroTarjeta, nombreTitular, fechaExp, cvv);
        } catch (Exception e) {
            System.out.println("[CheckoutDAO] Tarjeta no encontrada o inválida: " + e.getMessage());
            return null;
        }
    }

    // Obtener el monto total del pedido directamente desde la tabla pedido
public double obtenerMontoPedido(int idPedido) {
    String sql = """
                SELECT MontoFinal
                FROM pedido
                WHERE IDPedido = ?
                """;
    try {
        Double total = jdbcTemplate.queryForObject(sql, Double.class, idPedido);
        return total != null ? total : 0.0;
    } catch (Exception e) {
        System.err.println("[CheckoutDAO] Error al obtener monto total del pedido: " + e.getMessage());
        return 0.0;
    }
}


}