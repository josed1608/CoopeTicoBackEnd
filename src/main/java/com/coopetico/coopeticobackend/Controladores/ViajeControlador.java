package com.coopetico.coopeticobackend.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/viajes") // This means URL's start with /demo (after Application path)
public class ViajeControlador {
    @GetMapping(path="/restablecerContrasena")
    public @ResponseBody String addNewUser () {

        return "Hola joe";
    }
}