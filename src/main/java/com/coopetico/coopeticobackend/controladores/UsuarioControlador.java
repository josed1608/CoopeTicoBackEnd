package com.coopetico.coopeticobackend.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {
    @GetMapping(path="/contrasena")
    public @ResponseBody String recuperarContrasena (@RequestParam(value="correo") String correo) {

        return correo;
    }
}