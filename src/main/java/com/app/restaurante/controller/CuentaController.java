package com.app.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.dao.CuentaDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Direccion;
import com.app.restaurante.model.Pedido;

import jakarta.servlet.http.HttpSession;

@Controller
public class CuentaController {
    
    @Autowired
    private CuentaDAO cuentaDAO;

    @Autowired
    private HttpSession session;

    /**
     * Metodo principal que maneja la solicitud GET para mostrar la cuenta de un cliente
     */
    @GetMapping("/micuenta")
    public String mostrarCuenta(Model model) {
        // Obtener el idCliente de la sesión
        Long idCliente = (Long) session.getAttribute("idCliente");
        if (idCliente == null) {
            return "redirect:/login";
        }

        // Buscar el cliente por su id
        Cliente cliente = cuentaDAO.findById(idCliente);
        if (cliente == null) {
            model.addAttribute("mensaje", "Cliente no encontrado.");
            return "micuenta";
        }
        
        // Agregar el cliente al modelo
        model.addAttribute("cliente", cliente);

        // Obtener las direcciones del cliente
        List<Direccion> direcciones = cuentaDAO.obtenerDireccionesPorCliente(idCliente);
        model.addAttribute("direcciones", direcciones);

        // Establecer la página activa (por defecto "datos_personales")
        model.addAttribute("activePage", "datos_personales");

        return "micuenta";  // El nombre de la vista es "micuenta.html"
    }

    /**
     * Metodo para mostrar la sección de datos personales
     */
    @GetMapping("/datos_personales")
    public String datosPersonales(Model model) {
        Long idCliente = (Long) session.getAttribute("idCliente");
        if (idCliente == null) {
            return "redirect:/login";
        }

        // Buscar el cliente y obtener sus datos
        Cliente cliente = cuentaDAO.findById(idCliente);
        if (cliente == null) {
            model.addAttribute("mensaje", "Cliente no encontrado.");
            return "micuenta";
        }
        model.addAttribute("cliente", cliente);

        // Establecer la página activa
        model.addAttribute("activePage", "datos_personales");

        return "micuenta";
    }

    /**
     * Metodo para mostrar la sección de mis direcciones
     */
    @GetMapping("/mis_direcciones")
    public String misDirecciones(Model model) {
        Long idCliente = (Long) session.getAttribute("idCliente");
        if (idCliente == null) {
            return "redirect:/login";
        }

        // Obtener las direcciones del cliente
        List<Direccion> direcciones = cuentaDAO.obtenerDireccionesPorCliente(idCliente);
        model.addAttribute("direcciones", direcciones);

        // Establecer la página activa
        model.addAttribute("activePage", "mis_direcciones");

        return "micuenta";
    }

    /**
     * Metodo para mostrar la sección de mis pedidos
     */
    @GetMapping("/mis_pedidos")
    public String misPedidos(Model model) {
        Long idCliente = (Long) session.getAttribute("idCliente");
        if (idCliente == null) {
            return "redirect:/login";
        }

        // Obtener los pedidos del cliente
        List<Pedido> pedidos = cuentaDAO.obtenerPedidosPorCliente(idCliente);
        model.addAttribute("pedidos", pedidos);

        // Establecer la página activa
        model.addAttribute("activePage", "mis_pedidos");

        return "micuenta";
    }

    // Para procesar la actualizacion de  clientes
    @PostMapping("/cambiarDatos")
    public String registrarCliente(@RequestParam("nombre") String nombre,
                                   @RequestParam("apellido") String apellido,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("usuario") String usuario,
                                   RedirectAttributes redirectAttributes) {

        Long idCliente = (Long) session.getAttribute("idCliente");
                                    
        if (idCliente == null) {
            redirectAttributes.addFlashAttribute("error", "No se pudo obtener el idCliente.");
            return "redirect:/error";
        }

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente); 
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreo(correo);
        cliente.setUsuario(usuario);

        cuentaDAO.update(cliente);

        redirectAttributes.addFlashAttribute("mensaje", "Datos actualizados correctamente.");

        return "redirect:/micuenta";
    }
}

