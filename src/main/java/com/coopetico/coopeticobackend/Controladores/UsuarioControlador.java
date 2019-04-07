/**
 * Autor: Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 * 
 * Descripción: Este es el controlador Usuario.
 *
 **/
package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

// import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
// import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;

import com.coopetico.coopeticobackend.servicios.UsuariosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * TO_DO: comentar según las convenciones
 **/
@RestController
@RequestMapping("/api")
public class UsuarioControlador {
    /**
     * Variables globales de la clase UsuarioControlador
     **/
    private UsuariosServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuariosServicio usrSer) {
        this.usuarioServicio = usrSer;
    }

    /**
     * TO_DO: comentar según las convenciones.
     **/
    @GetMapping(path="/usuarios/{correo}")
    public UsuarioEntidad obtener_usuario (@PathVariable String correo) {
        return this.usuarioServicio.consultarPorId(correo);
    }
}