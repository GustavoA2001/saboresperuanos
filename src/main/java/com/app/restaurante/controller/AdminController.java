package com.app.restaurante.controller;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.dao.EmpleadosDAO;
import com.app.restaurante.dao.ProductosDAO;
import com.app.restaurante.dao.ParametrosDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Empleados;
import com.app.restaurante.model.Productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private EmpleadosDAO empleadosDAO;

    @Autowired
    private ProductosDAO productosDAO;

    // Página principal del panel (bienvenida)
    @GetMapping("/admin")
    public String adminInicio(Model model) {
        model.addAttribute("activeSection", "inicio");
        return "admin/adminMenu"; // Página principal del menú
    }

    // =========================================
    // EMPLEADOS
    // =========================================
    @GetMapping("/admin/empleados")
    public String mostrarEmpleados(Model model) {
        List<Empleados> empleados = empleadosDAO.listarEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("activeSection", "empleados");
        return "admin/admin_empleados";
    }

    @PostMapping("/admin/empleado/registrar")
    public String registrarEmpleado(@ModelAttribute Empleados empleado, RedirectAttributes redirectAttributes) {
        try {
            int idGenerado = empleadosDAO.registrarEmpleadoCompleto(empleado);
            System.out.println("Empleado insertado con ID: " + idGenerado);

            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado correctamente. ID: " + idGenerado);
        } catch (Exception e) {
            System.out.println("Error al registrar empleado:");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar el empleado: " + e.getMessage());
        }

        System.out.println("Redirigiendo a /admin/empleados");
        return "redirect:/admin/empleados";
    }

    // =========================================
    // PRODUCTOS
    // =========================================
    @GetMapping("/admin/productos")
    public String mostrarProductos(Model model) {
        List<Productos> productos = productosDAO.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("activeSection", "productos");
        return "admin/admin_productos";
    }

    // =========================================
    // CLIENTES
    // =========================================
    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/admin/clientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = clienteDAO.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("activeSection", "clientes");
        return "admin/admin_clientes";
    }

    // =========================================
    // PARAMETROS
    // =========================================
    @Autowired
    private ParametrosDAO parametrosDAO;

    @GetMapping("/admin/parametros")
    public String mostrarParametros(Model model) {
        model.addAttribute("roles", parametrosDAO.obtenerRoles());
        model.addAttribute("categorias", parametrosDAO.obtenerCategorias());
        model.addAttribute("distritos", parametrosDAO.obtenerDistritos());
        model.addAttribute("activeSection", "parametros");
        return "admin/admin_parametros";
    }

    // =========================================
    // CARTA
    // =========================================
    @GetMapping("/admin/carta")
    public String gestionarCarta(Model model) {
        List<Map<String, Object>> productosDiaAnterior = productosDAO.obtenerProductosDiaAnterior();
        List<Map<String, Object>> productosRecientes = productosDAO.obtenerProductosRecientes();
        List<Map<String, Object>> productosPasados = productosDAO.obtenerProductosPasados();

        model.addAttribute("productosPasados", productosPasados);

        model.addAttribute("productosDiaAnterior", productosDiaAnterior);
        model.addAttribute("productosRecientes", productosRecientes);
        model.addAttribute("activeSection", "carta");
        return "admin/admin_carta";
    }

}
