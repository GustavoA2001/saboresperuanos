package com.app.restaurante.controller;

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

    // Vista final de confirmación
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(@RequestParam("idPedido") Integer idPedido, Model model) {
        model.addAttribute("idPedido", idPedido);
        return "confirmacion";
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
            if (numeroTarjeta.length() != 16 || cvv.length() != 3) {
                return Map.of("success", false, "message", "Datos de tarjeta inválidos");
            }

            // Simula retardo de validación
            Thread.sleep(2000);

            // 1. Validar tarjeta en BD
            Integer idTarjeta = checkoutDAO.validarTarjeta(numeroTarjeta, nombreTitular, fechaExp, cvv);
            if (idTarjeta == null) {
                return Map.of("success", false, "message", "Tarjeta no registrada o inválida");
            }

            // 2. Obtener el monto del pedido
            double subtotal = checkoutDAO.obtenerMontoPedido(idPedido);
            double costoEnvio = 5.00;
            double totalPago = subtotal + costoEnvio;

            // 3. Actualizar estado del pedido a "Pagado"
            checkoutDAO.actualizarEstadoPedido(idPedido, "Pagado", "Tarjeta");

            // 4. Crear registro de delivery
            checkoutDAO.crearDeliveryParaPedido(idPedido, costoEnvio);

            // 5. Registrar el pago con la tarjeta validada
            checkoutDAO.registrarPago(idPedido, idTarjeta, subtotal);

            return Map.of(
                    "success", true,
                    "message", "Pago exitoso. Delivery y pago registrados.",
                    "subtotal", subtotal,
                    "envio", costoEnvio,
                    "total", totalPago);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "Error en el servidor: " + e.getMessage());
        }
    }

}