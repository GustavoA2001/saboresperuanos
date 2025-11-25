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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
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

        System.out.println("\n\n====== [CONTROLLER] Entrando a /admin/carta ======");

        List<Map<String, Object>> hoy = productosDAO.obtenerProductosHoy();
        List<Map<String, Object>> historicos = productosDAO.obtenerProductosHistoricos();

        System.out.println("Productos HOY: " + hoy.size());
        System.out.println("Productos HISTÓRICOS: " + historicos.size());

        System.out.println("\n------ [CONTROLLER] DETALLE DE PRODUCTOS HOY ------");
        hoy.forEach(p -> {
            System.out.println("--------------------------------------------------");
            p.forEach((key, value) -> {
                System.out.println(" " + key + " → " + value + " (tipo: " +
                        (value != null ? value.getClass().getSimpleName() : "NULL") + ")");
            });
        });
        System.out.println("--------------------------------------------------\n");

        model.addAttribute("productosRecientes", hoy);
        model.addAttribute("productosHistoricos", historicos);

        model.addAttribute("activeSection", "carta");

        System.out.println("====== [CONTROLLER] Datos enviados a la vista ======\n");

        return "admin/admin_carta";
    }

    @PostMapping("/admin/carta/agregar")
    @ResponseBody
    public Map<String, Object> agregarProductoHoy(@RequestBody Map<String, Object> params) {

        System.out.println("\n\n=== [POST] /admin/carta/agregar ===");
        System.out.println("Raw RequestBody: " + params);

        Map<String, Object> respuesta = new HashMap<>();

        try {
            // ======================
            // 1. LOG DE LOS DATOS
            // ======================
            System.out.println("params.get(\"idProdHistoria\") => " + params.get("idProdHistoria"));
            System.out.println("params.get(\"precio\") => " + params.get("precio"));
            System.out.println("params.get(\"cantidad\") => " + params.get("cantidad"));

            // ======================
            // 2. CONVERSIÓN
            // ======================
            Integer idProdHistoria = Integer.parseInt(params.get("idProdHistoria").toString());
            BigDecimal precio = new BigDecimal(params.get("precio").toString());
            Integer cantidad = Integer.parseInt(params.get("cantidad").toString());

            System.out.println("Convertidos -> id=" + idProdHistoria + ", precio=" + precio + ", cantidad=" + cantidad);

            // ======================
            // 3. VALIDAR EXISTENCIA
            // ======================
            System.out.println("Validando existencia del producto histórico...");
            Map<String, Object> producto = productosDAO.validarProductoActivo(idProdHistoria);

            System.out.println("Resultado validarProductoActivo: " + producto);

            if (producto == null) {
                respuesta.put("ok", false);
                respuesta.put("mensaje", "El producto no existe o está inactivo.");
                System.out.println("Producto NO válido.");
                return respuesta;
            }

            // ======================
            // 4. VALIDAR QUE NO EXISTA HOY
            // ======================
            System.out.println("Validando si ya existe hoy...");
            boolean existeHoy = productosDAO.existeProductoHoy(idProdHistoria);

            System.out.println("existeProductoHoy => " + existeHoy);

            if (existeHoy) {
                respuesta.put("ok", false);
                respuesta.put("mensaje", "Este producto ya fue agregado para hoy.");
                System.out.println("Producto YA existe hoy.");
                return respuesta;
            }

            // ======================
            // 5. INSERTAR
            // ======================
            System.out.println("Insertando producto...");
            productosDAO.insertarProductoHoy(idProdHistoria, precio, cantidad);

            System.out.println("✔ Producto guardado correctamente.");

            respuesta.put("ok", true);
            respuesta.put("mensaje", "Producto agregado correctamente.");

        } catch (Exception e) {
            System.out.println("ERROR EN EL PROCESO");
            e.printStackTrace();
            respuesta.put("ok", false);
            respuesta.put("mensaje", "Error interno al agregar el producto.");
        }

        return respuesta;
    }

    // Cambiar visibilidad
    @PostMapping("/admin/carta/visible/{idProducto}/{estado}")
    @ResponseBody
    public void cambiarVisible(@PathVariable int idProducto, @PathVariable int estado) {
        productosDAO.actualizarVisible(idProducto, estado);
    }

    // Eliminar instancia diaria
    @PostMapping("/admin/carta/eliminar/{idProducto}")
    @ResponseBody
    public void eliminarProducto(@PathVariable int idProducto) {
        productosDAO.eliminarProducto(idProducto);
    }

    // Cerrar carta del día
    @PostMapping("/admin/carta/cerrar")
    @ResponseBody
    public void cerrarCarta() {
        productosDAO.cerrarCartaHoy();
    }

    // =========================================
    // REPORTE
    // =========================================
    // SE CREO UN CONTROLLER PARA LOS REPORTES
}
