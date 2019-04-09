package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {
    @Autowired
    TokensRecuperacionContrasenaServicioImpl tokensServicio;

    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@RequestParam("correo") String correo) {
        if (tokensServicio.insertarToken(correo) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND );
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}