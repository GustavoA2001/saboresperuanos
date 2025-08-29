package com.app.restaurante.controller;

import com.app.restaurante.dao.ProductosDAO;
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
    public String mostrarCarta(Model model) {
        // Obtiene los productos separados por categorías
        List<Productos> bebidas = productosDAO.obtenerPorCategoria("Bebidas");
        List<Productos> postres = productosDAO.obtenerPorCategoria("Postres");
        List<Productos> platillos = productosDAO.obtenerPorCategoria("Platillos");
    
        // Asigna imagen por defecto a bebidas si no tienen una
        for (Productos producto : bebidas) {
            if (producto.getFotoProducto() == null || producto.getFotoProducto().isEmpty()) {
                producto.setFotoProducto("default");
            }
        }
        
        // Asigna imagen por defecto a postres si no tienen una
        for (Productos producto : postres) {
            if (producto.getFotoProducto() == null || producto.getFotoProducto().isEmpty()) {
                producto.setFotoProducto("default");
            }
        }
        
        // Asigna imagen por defecto a platillos si no tienen una
        for (Productos producto : platillos) {
            if (producto.getFotoProducto() == null || producto.getFotoProducto().isEmpty()) {
                producto.setFotoProducto("default");
            }            
        }
        
        // Agrega las listas de productos y la pagina activa al modelo
        model.addAttribute("bebidas", bebidas);
        model.addAttribute("postres", postres);
        model.addAttribute("platillos", platillos);
        model.addAttribute("activePage", "carta");
    
        //Incorpora los atributos del Cliente
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente); 

        return "carta";
    }

    /**
     * Metodo que maneja la solicitud GET para obtener los detalles de un producto especifico
     */
    @GetMapping("/producto/{idProducto}")
    public String obtenerDetalleProducto(@PathVariable("idProducto") int idProducto, Model model) {

        //Incorpora los atributos del Cliente
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente); 
        
        Productos producto = productosDAO.obtenerProductoPorId(idProducto);
        model.addAttribute("producto", producto);
        return "modalproducto";
    }


    /*
     * 
     */
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

        // Agregar mensaje de éxito y limpiar el formulario
        redirectAttributes.addFlashAttribute("mensaje", "Producto registrado exitosamente");
        redirectAttributes.addFlashAttribute("limpiarFormulario", true);

        return "redirect:/carta";
    }

    /**
     * Metodo que muestra todos los productos disponibles
     */
    @GetMapping("/productocarta")
    public String mostrarTodosProductos(Model model) {
        List<Productos> productos = productosDAO.findAll();
        model.addAttribute("productos", productos);
        return "productocarta";
    }


    /*** APARTADO PARA LA GESTIONAR LOS PRODUCTOS EN LA VISTA DEL EMPLEADO ****/


    
}
