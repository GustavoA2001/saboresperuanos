package com.app.restaurante.controller;

import com.app.restaurante.dao.ProductosDAO;
import com.app.restaurante.model.Categoria;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Productos;

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
    public String mostrarCarta(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(required = false) Integer tamaño,
            @RequestParam(required = false) Long categoriaId,
            Model model) {

        // Calcular el offset
        if (tamaño == null || tamaño <= 0) {
            tamaño = 12; // fallback por defecto
        }

        int offset = (pagina - 1) * tamaño;

        List<Productos> productos;
        int totalProductos;

        if (categoriaId != null) {
            productos = productosDAO.obtenerPorCategoriaId(categoriaId, offset, tamaño);
            totalProductos = productosDAO.countByCategoria(categoriaId);
        } else {
            productos = productosDAO.findAllPaginated(offset, tamaño);
            totalProductos = productosDAO.countAll();
        }

        int totalPaginas = (int) Math.ceil((double) totalProductos / tamaño);

        for (Productos producto : productos) {
            if (producto.getFotoProducto() == null || producto.getFotoProducto().isEmpty()) {
                producto.setFotoProducto("default");
            }
        }

        // Agrega datos al modelo
        model.addAttribute("productos", productos);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", totalPaginas);
        model.addAttribute("activePage", "carta");

        // Enviar categorías y la activa
        List<Categoria> categorias = productosDAO.findAllCategorias();
        System.out.println("Categorías encontradas: " + categorias.size());
        System.out.println("Categorías encontradas: " + categorias.size());
        System.out.println("Categorías encontradas: " + categorias.size());

        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaId", categoriaId);

        // Cliente logueado
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);

        return "carta";
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
