package com.coopetico.coopeticobackend.Controladores;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * TO_DO: comentar según las convenciones
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuariosRepositorio USUARIOS;
    private final ClientesRepositorio CLIENTES;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio users, ClientesRepositorio clnt) {
        this.USUARIOS = users;
        this.CLIENTES = clnt;
    }

    /*
     * TO_DO: comentar según las convenciones.
     */
    @GetMapping(path="/obtener_usuario")
    public @ResponseBody UsuarioEntidad/*TO_TO: String*/ obtener_usuario (String correo) {
        UsuarioEntidad resultado = null;
        try {
            UsuarioEntidad usuario_entidad = this.USUARIOS.findById(correo).orElseThrow(() -> new UsernameNotFoundException("no_usuario"));
            ClienteEntidad cliente_entidad = this.CLIENTES.findById(correo).orElseThrow(() -> new UsernameNotFoundException("no_cliente"));
            resultado = usuario_entidad;
            // resultado = "<p>";
            // resultado +="Nombre: " + usuario_entidad.getNombre();
            // resultado += "</br>Apellidos: " + usuario_entidad.getApellidos();
            // resultado += "</br>Telefono: " + usuario_entidad.getTelefono();
            // resultado += "</p>";
        } catch (Exception e) {
            // TO_DO: comment all this part.
            // String mensaje = "ERROR no manejado";
            // if (e.getMessage() == "no_usuario") {
            //     mensaje = "El usuario no existe";
            // } else if (e.getMessage() == "no_cliente") {
            //     mensaje = "El usuario no un cliente";
            // }
        }
        return resultado;
    }
}