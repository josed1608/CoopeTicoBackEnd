package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Email;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
@Validated
public class UsuarioControlador {
    @Autowired
    TokensRecuperacionContrasenaServicioImpl tokensServicio;
    @Autowired
    EmailServiceImpl mail ;

    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@Email @RequestParam("correo") String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND );
        }
        mail.enviarCorreoRecuperarCoontrasena(correo, token);
        return new ResponseEntity(HttpStatus.OK);
    }
}