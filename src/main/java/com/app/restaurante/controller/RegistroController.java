package com.app.restaurante.controller;

import com.app.restaurante.dao.ClienteDAO;
import com.app.restaurante.model.Cliente;
import com.app.restaurante.service.EncriptacionService;
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

import java.util.Map;

@Controller
public class RegistroController {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private HttpSession session;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EncriptacionService encriptacionService;

    private final String API_URL = "https://apiperu.dev/api/dni/";
    private final String API_TOKEN = "5374cc314f74f8d7193c53d6299c6b24a33afec5502bd3fec6869b38029ee5fa";

    @GetMapping("/registro")
    public String showRegistro(Model model) {
        return "registrarse";
    }

    @PostMapping("/registrarse")
    public String registrarCliente(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("correo") String correo,
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("repetircontrasena") String repetirContrasena,
            RedirectAttributes redirectAttributes) {

        // Validar DNI duplicado
        if (clienteDAO.existsByDni(dni)) {
            redirectAttributes.addFlashAttribute("mensaje", "El DNI ya está registrado.");
            redirectAttributes.addFlashAttribute("active", "error");
            return "redirect:/registro";
        }

        // Validar correo duplicado
        if (clienteDAO.existsByCorreo(correo)) {
            redirectAttributes.addFlashAttribute("mensaje", "El correo ya está registrado. Intenta con otro.");
            redirectAttributes.addFlashAttribute("active", "error");
            return "redirect:/registro";
        }

        // Validar usuario duplicado
        if (clienteDAO.existsByUsuario(usuario)) {
            redirectAttributes.addFlashAttribute("mensaje", "El nombre de usuario ya está en uso.");
            redirectAttributes.addFlashAttribute("active", "error");
            return "redirect:/registro";
        }

        // Validar contraseñas
        if (!contrasena.equals(repetirContrasena)) {
            redirectAttributes.addFlashAttribute("mensaje", "Las contraseñas no coinciden.");
            redirectAttributes.addFlashAttribute("active", "error");
            return "redirect:/registro";
        }

        // Validar DNI con API
        Map<String, Object> dniData = validarDni(dni);
        if (dniData == null || dniData.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensaje", "DNI inválido o no encontrado.");
            redirectAttributes.addFlashAttribute("active", "error");
            return "redirect:/registro";
        }

        // Encriptar contraseña
        String hashedPassword = encriptacionService.encriptarMD5(contrasena);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setCorreo(correo);
        cliente.setUsuario(usuario);
        cliente.setContrasena(hashedPassword);

        clienteDAO.save(cliente);

        session.setAttribute("idCliente", cliente.getIdCliente());
        session.setAttribute("usuario", cliente.getUsuario());
        session.setAttribute("nombre", cliente.getNombre());
        session.setAttribute("cliente", cliente);

        redirectAttributes.addFlashAttribute("mensaje", "¡Registro exitoso! Bienvenido a Sabores Peruanos 🍽️");
        redirectAttributes.addFlashAttribute("active", "success");

        return "redirect:/";
    }

    private Map<String, Object> validarDni(String dni) {
        try {
            String url = API_URL + dni + "?api_token=" + API_TOKEN;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                if (body.get("data") != null) {
                    return (Map<String, Object>) body.get("data");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
