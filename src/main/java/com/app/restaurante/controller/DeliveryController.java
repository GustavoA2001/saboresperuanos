package com.app.restaurante.controller;

import com.app.restaurante.dao.DeliveryDAO;
import com.app.restaurante.model.Pedido;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeliveryController {

    private final DeliveryDAO deliveryDao;

    private final HttpSession session;

    public DeliveryController(DeliveryDAO deliveryDao, HttpSession session) {
        this.deliveryDao = deliveryDao;
        this.session = session;
    }

    @GetMapping("/delivery")
    public String mostrarDelivery(Model model) {
        return "delivery"; 
    }

    @GetMapping("/mis_entregas")
    public String misEntregas(Model model) {
        Integer idEmpleado = (Integer) session.getAttribute("idEmpleado");

        System.out.println(idEmpleado);
        System.out.println(idEmpleado);
        System.out.println(idEmpleado);

        // Asegúrate de que la lista de pedidos nunca sea null
        List<Pedido> pedidos = deliveryDao.obtenerPedidosEmpleado();
        if (pedidos == null) {
            pedidos = new ArrayList<>(); // Asignar una lista vacía si es null
        }

        model.addAttribute("pedidos", pedidos); 
        model.addAttribute("activePage", "mis_entregas");
        return "delivery";
    }


    @GetMapping("/seleccionar_pedido")
    public String listaDePedidos(Model model) {

        Integer idEmpleado = (Integer) session.getAttribute("idEmpleado");

        System.out.println(idEmpleado);
        System.out.println(idEmpleado);
        System.out.println(idEmpleado);

        List<Pedido> pedidos = deliveryDao.obtenerPedidosDisponibles();
        model.addAttribute("pedidos", pedidos); 
        model.addAttribute("activePage", "seleccionar_pedido");
        return "delivery";  
    }

    @PostMapping("/seleccionar_delivery")
    public String seleccionarPedido(@RequestParam Integer idPedido, 
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        
        Integer idEmpleado = (Integer) session.getAttribute("idEmpleado");

        System.out.println(idEmpleado);
        System.out.println(idEmpleado);
        System.out.println(idEmpleado);

        if (idEmpleado == null) {
            model.addAttribute("error", "No se ha encontrado el empleado en la sesión.");
            return "error";  // Redirigir a una página de error si no se encuentra el empleado
        }

        System.out.println(idPedido);
        System.out.println(idPedido);
        System.out.println(idPedido);

        // Buscar el IDDelivery asociado al idPedido
        Integer idDelivery = deliveryDao.obtenerIdDeliveryPorIdPedido(idPedido);

        System.out.println(idDelivery);
        System.out.println(idDelivery);
        System.out.println(idDelivery);

        // Verificamos si se encontró un idDelivery
        if (idDelivery == null) {
            model.addAttribute("error", "El pedido no tiene un delivery asociado.");
            return "error";  // Redirigir a una página de error o mostrar mensaje
        }

        // Registrar el idEmpleado junto con el idDelivery en la tabla delivery
        boolean exito = deliveryDao.registrarEmpleadoEnDelivery(idEmpleado, idDelivery);

        // Verificamos si el registro fue exitoso
        // Verificamos si el registro fue exitoso
        if (exito) {
            model.addAttribute("mensaje", "Pedido seleccionado correctamente");
            return "redirect:/delivery";  // Redirigir para evitar el reenvío del formulario
        } else {
            model.addAttribute("error", "Hubo un problema al registrar el delivery.");
            return "error";  // Redirigir a una página de error
        }
    }

}
