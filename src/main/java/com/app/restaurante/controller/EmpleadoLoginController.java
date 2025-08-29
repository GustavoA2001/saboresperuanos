package com.app.restaurante.controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.service.EmpleadoService;
import com.app.restaurante.utils.Validation;

@Controller
public class EmpleadoLoginController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HttpSession session;

    @GetMapping("/loginEmpleado")
    public String mostrarLogin() {
        return "login"; // Asegúrate de tener una vista llamada login.html
    }

    @PostMapping("/loginEmpleado")
    public ModelAndView loginEmpleado(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String password,
            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, IOException {

        // Validar las credenciales del empleado
        Empleado empleado = empleadoService.validateUser(usuario, password); // La contraseña se hasheará en el servicio

        if (empleado != null) {
            // Almacena el empleado en la sesión
            session.setAttribute("idEmpleado", empleado.getIdEmpleado());
            session.setAttribute("usuario", empleado.getUsuario());
            session.setAttribute("nombre", empleado.getNombre());

            // Verificar el rol y redirigir
            if (empleado.getRol().getIdRol() == 1) { // Verifica que el rol sea 1
                return new ModelAndView("redirect:/empleados");
            } else if (empleado.getRol().getIdRol() == 4) { // Verifica si el rol es 4
                return new ModelAndView("redirect:/delivery"); // Redirige a la página de delivery
            } else {
                // Puedes agregar más roles aquí si es necesario
                redirectAttributes.addFlashAttribute("error", "Acceso no permitido");
                return new ModelAndView("redirect:/loginEmpleado");
            }
        } else {
            // Si las credenciales son incorrectas, redirige al login con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña inválidos");
            return new ModelAndView("redirect:/loginEmpleado");
        }
    }
}
/*
 * package com.app.restaurante.controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.service.EmpleadoService;
import com.app.restaurante.utils.Validation;

@Controller
public class EmpleadoLoginController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HttpSession session;

    @GetMapping("/loginEmpleado")
    public String mostrarLogin() {
        return "login"; // Asegúrate de tener una vista llamada login.html
    }

    @PostMapping("/loginEmpleado")
    public ModelAndView loginEmpleado(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String password,
            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, IOException {

        // Validar las credenciales del empleado
        Empleado empleado = empleadoService.validateUser(usuario, password); // La contraseña se hasheará en el servicio

        if (empleado != null) {
            // Almacena el empleado en la sesión
            session.setAttribute("idEmpleado", empleado.getIdEmpleado());
            session.setAttribute("usuario", empleado.getUsuario());
            session.setAttribute("nombre", empleado.getNombre());

            // Verificar el rol y redirigir
            if (empleado.getRol().getIdRol() == 1) { // Verifica que el rol sea 1
                return new ModelAndView("redirect:/empleados");
            } else if (empleado.getRol().getIdRol() == 4) { // Verifica si el rol es 4
                return new ModelAndView("redirect:/delivery"); // Redirige a la página de delivery
            } else {
                // Puedes agregar más roles aquí si es necesario
                redirectAttributes.addFlashAttribute("error", "Acceso no permitido");
                return new ModelAndView("redirect:/loginEmpleado");
            }
        } else {
            // Si las credenciales son incorrectas, redirige al login con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña inválidos");
            return new ModelAndView("redirect:/loginEmpleado");
        }
    }
}

 */

 /*
  * package com.app.restaurante.controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.restaurante.model.Empleado;
import com.app.restaurante.service.EmpleadoService;
import com.app.restaurante.utils.Validation;

@Controller
public class EmpleadoLoginController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HttpSession session;

    @GetMapping("/loginEmpleado")
    public String mostrarLogin() {
        return "login"; // Asegúrate de tener una vista llamada login.html
    }

    @PostMapping("/loginEmpleado")
    public ModelAndView loginEmpleado(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String password,
            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, IOException {

        // Validar las credenciales del empleado
        Empleado empleado = empleadoService.validateUser(usuario, password); // La contraseña se hasheará en el servicio

        if (empleado != null) {
            // Almacena el empleado en la sesión
            session.setAttribute("idEmpleado", empleado.getIdEmpleado());
            session.setAttribute("usuario", empleado.getUsuario());
            session.setAttribute("nombre", empleado.getNombre());

            // Usamos un switch para redirigir según el idRol
            switch (empleado.getRol().getIdRol()) {
                case 1:
                    return new ModelAndView("redirect:/empleados"); // Redirige a empleados
                case 4:
                    return new ModelAndView("redirect:/delivery"); // Redirige a delivery
                default:
                    // Si el rol no es 1 ni 4, se muestra un mensaje de error
                    redirectAttributes.addFlashAttribute("error", "Acceso no permitido");
                    return new ModelAndView("redirect:/loginEmpleado");
            }
        } else {
            // Si las credenciales son incorrectas, redirige al login con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña inválidos");
            return new ModelAndView("redirect:/loginEmpleado");
        }
    }
}

  */