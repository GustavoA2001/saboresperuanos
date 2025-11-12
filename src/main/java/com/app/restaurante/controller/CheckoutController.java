package com.app.restaurante.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.dao.CarritoDAO;
import com.app.restaurante.dao.CheckoutDAO;
import com.app.restaurante.model.Carrito;
import com.app.restaurante.model.Cliente;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutDAO checkoutDAO;

    @Autowired
    private CarritoDAO carritoDAO;

    @Autowired
    private HttpSession session;

    // Mostrar el resumen de compra
    @GetMapping("/checkout")
    public String mostrarCheckout(@RequestParam("idPedido") Integer idPedido, Model model) {

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        // Obtener productos del carrito
        List<Carrito> carrito = carritoDAO.obtenerDetallesCarrito(idPedido);
        if (carrito.isEmpty()) {
            return "redirect:/carrito_compra";
        }

        // Direcciones del cliente
        List<Map<String, Object>> direcciones = checkoutDAO.obtenerDireccionesPorCliente(cliente.getIdCliente());

        // Distritos para el formulario
        List<Map<String, Object>> distritos = checkoutDAO.obtenerDistritos();
        // Calcular total
        double total = carrito.stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();

        model.addAttribute("cliente", cliente);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("idPedido", idPedido);
        model.addAttribute("direcciones", direcciones);
        model.addAttribute("distritos", distritos);

        return "checkout";
    }

    // Confirmar el pago
    @PostMapping("/checkout/pagar")
    public String procesarPago(@RequestParam("idPedido") Integer idPedido,
            @RequestParam("metodoPago") String metodoPago,
            RedirectAttributes redirectAttributes) {

        try {
            checkoutDAO.actualizarEstadoPedido(idPedido, "Pagado", metodoPago);
            redirectAttributes.addFlashAttribute("mensaje", "Pago realizado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
        }

        return "redirect:/confirmacion?idPedido=" + idPedido;
    }

    @PostMapping("/checkout/agregarDireccion")
    public String agregarDireccion(
            @RequestParam("direccionCompleta") String direccionCompleta,
            @RequestParam("referencia") String referencia,
            @RequestParam("idDistrito") Integer idDistrito,
            @RequestParam("idPedido") Integer idPedido,
            RedirectAttributes redirectAttributes) {

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null)
            return "redirect:/login";

        checkoutDAO.agregarDireccion(cliente.getIdCliente(), direccionCompleta, referencia, idDistrito);
        redirectAttributes.addFlashAttribute("mensaje", "Nueva dirección guardada correctamente.");

        return "redirect:/checkout?idPedido=" + idPedido;
    }

    @PostMapping("/checkout/seleccionarDireccion")
    public String seleccionarDireccion(@RequestParam("idDireccion") Integer idDireccion,
            @RequestParam("idPedido") Integer idPedido,
            RedirectAttributes redirectAttributes) {
        checkoutDAO.asignarDireccionPedido(idPedido, idDireccion);
        redirectAttributes.addFlashAttribute("mensaje", "Dirección seleccionada correctamente.");
        return "redirect:/checkout?idPedido=" + idPedido;
    }

    @PostMapping("/checkout/api/seleccionarDireccion")
    @ResponseBody
    public Map<String, Object> seleccionarDireccionAjax(
            @RequestParam("idDireccion") Integer idDireccion,
            @RequestParam("idPedido") Integer idPedido) {

        checkoutDAO.asignarDireccionPedido(idPedido, idDireccion);

        Map<String, Object> direccion = checkoutDAO.obtenerDireccionPorId(idDireccion);

        return Map.of(
                "success", true,
                "direccion", direccion);
    }

    @PostMapping("/checkout/api/agregarDireccion")
    @ResponseBody
    public Map<String, Object> agregarDireccionAjax(
            @RequestParam("direccionCompleta") String direccionCompleta,
            @RequestParam("referencia") String referencia,
            @RequestParam("idDistrito") Integer idDistrito,
            @RequestParam("idPedido") Integer idPedido) {

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return Map.of("success", false, "error", "Sesión expirada");
        }

        checkoutDAO.agregarDireccion(cliente.getIdCliente(), direccionCompleta, referencia, idDistrito);

        // obtener la última dirección agregada
        Map<String, Object> nuevaDireccion = checkoutDAO.obtenerUltimaDireccion(cliente.getIdCliente());

        // asignar al pedido
        checkoutDAO.asignarDireccionPedido(idPedido, (Integer) nuevaDireccion.get("IDDireccion"));

        return Map.of(
                "success", true,
                "direccion", nuevaDireccion);
    }

    @PostMapping("/checkout/api/simularPago")
    @ResponseBody
    public Map<String, Object> simularPago(
            @RequestParam("idPedido") Integer idPedido,
            @RequestParam("numeroTarjeta") String numeroTarjeta,
            @RequestParam("nombreTitular") String nombreTitular,
            @RequestParam("fechaExp") String fechaExp,
            @RequestParam("cvv") String cvv) {

        try {
            // Validación básica
            if (numeroTarjeta.length() != 16 || cvv.length() != 3) {
                return Map.of("success", false, "message", "Datos de tarjeta inválidos");
            }

            Thread.sleep(1500); // simulación validación

            // 1 Validar tarjeta
            Integer idTarjeta = checkoutDAO.validarTarjeta(numeroTarjeta, nombreTitular, fechaExp, cvv);
            if (idTarjeta == null) {
                return Map.of("success", false, "message", "Tarjeta no registrada o inválida");
            }

            // 2 Subtotal (solo productos)
            double subtotal = checkoutDAO.obtenerMontoPedido(idPedido);

            // 3 IGV (18%)
            BigDecimal subtotalBD = BigDecimal.valueOf(subtotal);
            BigDecimal igvBD = subtotalBD.multiply(BigDecimal.valueOf(0.18)).setScale(2, RoundingMode.HALF_UP);

            // 4 Envío variable
            BigDecimal costoEnvioBD;
            if (subtotal >= 100) {
                costoEnvioBD = BigDecimal.ZERO;
            } else {
                costoEnvioBD = subtotalBD.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
            }

            // 5 Total general (para el pago)
            BigDecimal totalPagoBD = subtotalBD.add(igvBD).add(costoEnvioBD).setScale(2, RoundingMode.HALF_UP);

            // 6 Obtener saldo actual de la tarjeta
            Double saldoActual = checkoutDAO.obtenerSaldoTarjeta(idTarjeta);
            if (saldoActual == null) {
                return Map.of("success", false, "message", "No se pudo verificar el saldo de la tarjeta");
            }

            // 7 Verificar si hay saldo suficiente
            if (saldoActual < totalPagoBD.doubleValue()) {
                return Map.of(
                        "success", false,
                        "message", "Saldo insuficiente. Verifique su método de pago o use otra tarjeta.");
            }

            // 8 Descontar saldo
            double nuevoSaldo = saldoActual - totalPagoBD.doubleValue();
            checkoutDAO.actualizarSaldoTarjeta(idTarjeta, nuevoSaldo);

            // 9 Guardar IGV en pedido
            checkoutDAO.actualizarIGVPedido(idPedido, igvBD.doubleValue());

            // 10 Crear registro de delivery
            checkoutDAO.crearDeliveryParaPedido(idPedido, costoEnvioBD.doubleValue());

            // 11 Registrar pago
            checkoutDAO.registrarPago(idPedido, idTarjeta, totalPagoBD.doubleValue());

            // 12 Actualizar estado del pedido
            checkoutDAO.actualizarEstadoPedido(idPedido, "Pagado", "Tarjeta");

            // 13 Retornar respuesta
            return Map.of(
                    "success", true,
                    "message", "Pago exitoso",
                    "subtotal", subtotalBD.doubleValue(),
                    "igv", igvBD.doubleValue(),
                    "envio", costoEnvioBD.doubleValue(),
                    "total", totalPagoBD.doubleValue(),
                    "saldoRestante", nuevoSaldo);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "Error interno: " + e.getMessage());
        }
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(
            @RequestParam(value = "idPedido", required = false) String idPedidoStr,
            @RequestParam(value = "error", required = false) String error,
            Model model) {

        Integer idPedido = null;
        if (idPedidoStr != null && !idPedidoStr.isBlank()) {
            try {
                idPedido = Integer.parseInt(idPedidoStr);
            } catch (NumberFormatException e) {
                // ignoramos si no es un número válido
            }
        }

        if (error != null) {
            model.addAttribute("exito", false);
            model.addAttribute("mensajeError", error);

            if (idPedido != null)
                model.addAttribute("idPedido", idPedido);
        } else {
            model.addAttribute("exito", true);
            model.addAttribute("idPedido", idPedido);
        }

        return "confirmacion";
    }

}