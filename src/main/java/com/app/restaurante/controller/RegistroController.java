package com.app.restaurante.controller;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.model.Direccion;
import com.app.restaurante.utils.Validation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Controller
public class RegistroController {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private HttpSession session;

    @Autowired
    private RestTemplate restTemplate;

    // API-Peru
    private final String API_URL = "https://apiperu.dev/api/dni/";
    private final String API_TOKEN = "5374cc314f74f8d7193c53d6299c6b24a33afec5502bd3fec6869b38029ee5fa";

    @GetMapping("/registro")
    public String showRegistro(Model model) {
        return "registrarse";
    }

    // Recibir y procesar el registro de nuevos clientes
    @PostMapping("/registrarse")
    public String registrarCliente(@RequestParam("nombre") String nombre,
                                   @RequestParam("apellido") String apellido,
                                   @RequestParam("dni") String dni,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("usuario") String usuario,
                                   @RequestParam("contrasena") String contrasena,
                                   @RequestParam("repetircontrasena") String repetirContrasena,
                                   RedirectAttributes redirectAttributes) {

        // Valida si el DNI ya está registrado
        if (clienteDAO.existsByDni(dni)) {
            redirectAttributes.addFlashAttribute("error", "El DNI ya está registrado");
            return "redirect:/login";
        }

        // Valida si las contraseñas coinciden
        if (!contrasena.equals(repetirContrasena)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/login";
        }

        // Valida si el DNI es valido usando el API
        Map<String, Object> dniData = validarDni(dni);
        if (dniData == null || dniData.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "DNI inválido o no encontrado");
            return "redirect:/login";
        }

        // Encripta la contraseña
        String hashedPassword;
        try {
            hashedPassword = Validation.md5(contrasena);
        } catch (NoSuchAlgorithmException e) {
            redirectAttributes.addFlashAttribute("error", "Error al encriptar la contraseña");
            return "redirect:/login";
        }

        // Crear y guardar el cliente
        // Podemos descartar el nombre y apellido en el formulario
        // Se pueden traer con la consulta del DNI
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        //nuevoCliente.setNombre((String) dniData.get("nombres"));
        cliente.setApellido(apellido);
        //nuevoCliente.setApellido(dniData.get("apellido_paterno") + " " + dniData.get("apellido_materno"));
        cliente.setDni(dni);
        cliente.setCorreo(correo);
        cliente.setUsuario(usuario);
        cliente.setContrasena(hashedPassword);

        clienteDAO.save(cliente);

        // Inicia sesion
        session.setAttribute("cliente", cliente);

        return "redirect:/registro_completar";
    }

    // Metodo para validar el DNI mediante el API 
    private Map<String, Object> validarDni(String dni) {
        
        try {
            String url = API_URL + dni + "?api_token=" + API_TOKEN;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            // Validar respuesta del API
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                if (body.get("data") != null) {
                    return (Map<String, Object>) body.get("data"); // Datos del DNI
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Si falla o no encuentra        
    }

    
    @GetMapping("/registro_completar")
    public String showBienvenido(Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        // Verifica si el cliente ya tiene dirección registrada
        boolean tieneDireccion = clienteDAO.hasDireccion(cliente.getIdCliente()); // Método en DAO
        model.addAttribute("cliente", cliente);
        model.addAttribute("mostrarModal", !tieneDireccion); // Muestra el modal si no tiene dirección

        // Obtenenemos los distritos de la base de datos
        // Se mostrara el formulario Distrito si no hay
        List<Direccion> distritos = clienteDAO.findAllDistritos();
        model.addAttribute("distritos", distritos);

        return "index";
    }


    
}

