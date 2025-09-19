package com.app.restaurante.controller;

import com.app.restaurante.dao.ProductosDAO;
import com.app.restaurante.model.Categoria;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Productos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los productos
 */
@Controller
public class ProductoController {

    @Autowired
    private ProductosDAO productosDAO;

    @Autowired
    private HttpSession session;


    /**
     * Metodo que maneja la solicitud GET para mostrar la carta de productos
     */
@GetMapping("/carta")
public String mostrarCarta(HttpSession session,
                           Model model,
                           @RequestParam(value = "query", required = false) String query,
                           @RequestParam(value = "categoria", required = false) Long idCategoria,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           HttpServletRequest request) {

    Cliente cliente = (Cliente) session.getAttribute("cliente");
    model.addAttribute("cliente", cliente);
    model.addAttribute("activePage", "carta");

    // Tamaño de página (ej. 12 productos por página)
    int pageSize = 12;
    int offset = (page - 1) * pageSize;

    // Cargar categorías
    List<Categoria> categorias = productosDAO.findAllCategorias();
    model.addAttribute("categorias", categorias);

    // Guardar la categoría seleccionada
    model.addAttribute("categoriaSeleccionada", idCategoria);

    // Variables de resultados
    List<Productos> productos;
    int totalProductos;

    if (query != null && !query.isEmpty()) {
        totalProductos = productosDAO.countByBusquedaPorNombre(query);
        productos = productosDAO.buscarProductosPorNombre(query, offset, pageSize);
    } else if (idCategoria != null) {
        totalProductos = productosDAO.countByCategoria(idCategoria);
        productos = productosDAO.obtenerPorCategoriaId(idCategoria, offset, pageSize);
    } else {
        totalProductos = productosDAO.countAll();
        productos = productosDAO.findAllPaginated(offset, pageSize);
    }

    model.addAttribute("productos", productos);

    // Calcular número total de páginas
    int totalPages = (int) Math.ceil((double) totalProductos / pageSize);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("currentPage", page);

    // Si es petición AJAX solo devolvemos los productos
    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
        return "carta :: productos";
    }

    return "carta";
}


    /**
     * Asigna imagen por defecto si el producto no tiene foto.
     */
    private void normalizarImagenes(List<Productos> productos) {
        for (Productos producto : productos) {
            if (producto.getFotoProducto() == null || producto.getFotoProducto().isEmpty()) {
                producto.setFotoProducto("default");
            }
        }
    }

    /**
     * Metodo que maneja la solicitud GET para obtener los detalles de un producto
     * especifico
     */
    @GetMapping("/producto/{idProducto}")
    public String obtenerDetalleProducto(@PathVariable("idProducto") int idProducto, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);

        Productos producto = productosDAO.obtenerProductoPorId(idProducto);
        model.addAttribute("producto", producto);
        return "modalproducto";
    }

    /**
     * Metodo que maneja la solicitud POST para registrar un nuevo producto
     */
    @PostMapping("/registrar_producto")
    public String registrarProducto(@RequestParam("nomProducto") String nomProducto,
            @RequestParam("precioUnitario") double precioUnitario,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("idCategoria") Long idCategoria,
            @RequestParam("idTipo") Long idTipo,
            @RequestParam("fotoProducto") String fotoProducto,
            RedirectAttributes redirectAttributes) {
        Productos nuevoProducto = new Productos();
        nuevoProducto.setNomProducto(nomProducto);
        nuevoProducto.setPrecioUnitario(precioUnitario);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setCantidad(cantidad);
        nuevoProducto.setIdCategoria(idCategoria);
        nuevoProducto.setIdTipo(idTipo);
        nuevoProducto.setFotoProducto(fotoProducto);

        productosDAO.save(nuevoProducto);

        redirectAttributes.addFlashAttribute("mensaje", "Producto registrado exitosamente");
        redirectAttributes.addFlashAttribute("limpiarFormulario", true);

        return "redirect:/carta";
    }

    /**
     * Metodo alternativo para mostrar todos los productos (opcional)
     */
    @GetMapping("/productocarta")
    public String mostrarTodosProductos(Model model) {
        List<Productos> productos = productosDAO.findAll();
        model.addAttribute("productos", productos);
        return "productocarta";
    }

}
