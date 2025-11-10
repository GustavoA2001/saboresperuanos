package com.app.restaurante.controller;

import com.app.restaurante.dao.*;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Carrito;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CarritoController {

    @Autowired
    private CarritoDAO carritoDAO;

    @Autowired
    private HttpSession session;

    /*
     * Método para registrar un producto al carrito
     */
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam("idproducto") Long idProducto,
                                   @RequestParam("cantidad") int cantidad,
                                   @RequestParam("precioUnitario") double precioUnitario,
                                   RedirectAttributes redirectAttributes) {
                                 
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        // Calcular el precioProducto
        double precioProducto = cantidad * precioUnitario;

        // Verificar si existe un pedido activo hoy para el cliente
        Integer idPedido = carritoDAO.obtenerUltimoPedidoPorCliente(cliente.getIdCliente());
        if (idPedido == null) {
            // Si no existe un pedido activo hoy, se crea uno nuevo
            idPedido = carritoDAO.crearNuevoPedido(cliente.getIdCliente()); // Se trae el id del pedido creado
        }

        // Guardar el producto en el carrito del pedido
        carritoDAO.guardarEnCarrito(cliente.getIdCliente(), idProducto, cantidad, precioUnitario, idPedido);
        
        // Retraso de 2 segundos antes de redirigir
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito exitosamente.");

        return "redirect:/carta";
    }

    /**
     * Método que maneja la solicitud GET para mostrar el carrito de compras por ID de pedido
     */
    @GetMapping("/carrito_compra")
    public String mostrarCarrito(Model model) {

        // Obtener el idCliente de la sesión
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        // Obtener el último idPedido para el cliente
        Integer idPedido = carritoDAO.obtenerUltimoPedidoPorCliente(cliente.getIdCliente());
        if (idPedido == null) {
            model.addAttribute("mensaje", "No hay pedidos asociados a este cliente.");
            return "carrito"; 
        }

        // Obtener los detalles del carrito asociados al idPedido
        List<Carrito> carritoDelPedido = carritoDAO.obtenerDetallesCarrito(idPedido);

        if (carritoDelPedido.isEmpty()) {
            model.addAttribute("mensaje", "No hay productos en el carrito.");
        }

        model.addAttribute("carrito", carritoDelPedido);
        model.addAttribute("idPedido", idPedido);

        // Calcular el total del carrito
        double total = carritoDelPedido.stream()
                                       .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario()) 
                                       .sum();
        model.addAttribute("total", total);

        // Agregar información del cliente al modelo
        model.addAttribute("cliente", cliente);

        return "carrito"; 
    }

    // Metodo para eliminar el producto del carrito
    @PostMapping("/carrito/eliminar/{idCarrito}/{idPedido}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long idCarrito, @PathVariable Long idPedido) {
        carritoDAO.eliminarProducto(idCarrito, idPedido);
        return ResponseEntity.ok().build();
    }

    
}
