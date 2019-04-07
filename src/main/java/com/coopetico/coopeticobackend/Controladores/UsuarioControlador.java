/**
 * Autor: Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 * <p>
 * Este es el controlador Usuario. Se encarga de la comunicación entre
 * la capa V y el M Cliente, especialización de Usuario.
 **/
package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.UsuariosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {
    /**
     * Variables globales de la clase UsuarioControlador
     **/
    private UsuariosServicio usuarioServicio;

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Constructor de la clase UsuarioControlador.
     *
     * @param usrSer el servicio de usuarios ya creado.
     */
    @Autowired
    public UsuarioControlador(UsuariosServicio usrSer) {
        this.usuarioServicio = usrSer;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Busca el cliente en la base de datos.
     *
     * @param correo El identificador del cliente.
     * @return UsuarioEntidad si el correo está en la tabla Cliente,
     * null de otra manera.
     */
    @GetMapping(path = "/usuarios/{correo}")
    public UsuarioEntidad obtener_usuario(@PathVariable String correo) {
        return this.usuarioServicio.consultarPorId(correo);
    }
}