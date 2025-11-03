package com.app.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurante.dao.ProductosDAO;
import com.app.restaurante.model.Productos;
import com.app.restaurante.service.UploadServicio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ProductAdminController {
    @Autowired
    private ProductosDAO productosDAO;
    @Autowired
    private UploadServicio uploadServicio;

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<Productos> productos = productosDAO.findAll(); // Obtener la lista de productos
        model.addAttribute("productos", productos); // Agregar la lista al modelo
        
        return "listProductos"; // Nombre de la vista que se mostrará
    
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Productos()); // Crear un producto vacío
        return "formProducto"; // Nombre de la vista del formulario
    }

    @PostMapping("/productos")
public String guardarProducto(@ModelAttribute Productos producto, @RequestParam("file") MultipartFile file) {
    try {
        // Verificar y establecer la fecha del producto si no está configurada
        if (producto.getFechaProducto() == null || producto.getFechaProducto().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            producto.setFechaProducto(LocalDateTime.now().format(formatter));
        }

        // Guardar el archivo de imagen si se proporciona
        String fileName = uploadServicio.saveUpload(file, "productos"); // Guardar en subcarpeta "productos"
        if (fileName != null) {
            producto.setFotoProducto(fileName); // Asignar nombre del archivo al producto
        }

        // Guardar el producto en la base de datos
        productosDAO.save(producto);

    } catch (IOException e) {
        e.printStackTrace();
        return "error"; // Redirigir a una página de error si ocurre un problema
    }

    return "redirect:/productos"; // Redirigir a la lista de productos tras guardar
}


        // NUEVO: Método para cargar el formulario de edición
    @GetMapping("/productos/editar/{id}")
    public String editarProductoForm(@PathVariable("id") Long id, Model model) {
        Productos producto = productosDAO.findById(id); // Buscar el producto por su ID
        if (producto == null) {
            return "error"; // Manejar el caso donde el producto no exista
        }
        model.addAttribute("producto", producto); // Agregar el producto al modelo
        return "formProductoEditar"; // Nombre de la vista del formulario de edición
    }

    // NUEVO: Método para procesar los cambios del formulario de edición
    @PostMapping("/productos/{id}")
    public String actualizarProducto(
            @PathVariable("id") Long id,
            @ModelAttribute Productos producto,
            @RequestParam("file") MultipartFile file) {
        try {
            Productos productoExistente = productosDAO.findById(id); // Buscar el producto existente
            if (productoExistente == null) {
                return "error"; // Manejar el caso donde el producto no exista
            }

            // Actualizar los campos del producto existente
            productoExistente.setNomProducto(producto.getNomProducto());
            productoExistente.setPrecioUnitario(producto.getPrecioUnitario());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setIdCategoria(producto.getIdCategoria());

            // Actualizar la imagen si se ha subido una nueva
            if (!file.isEmpty()) {
                String fileName = uploadServicio.saveUpload(file, "productos");
                productoExistente.setFotoProducto(fileName);
            }

            productosDAO.save(productoExistente); // Guardar los cambios en la base de datos
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "redirect:/productos"; // Redirigir a la lista de productos
    }

    // Método para eliminar un producto por su ID
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        try {
            productosDAO.deleteById(id); // Eliminar el producto por su ID
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Redirigir a una página de error si ocurre un problema
        }
        return "redirect:/productos"; // Redirigir a la lista de productos tras la eliminación
    }

}
