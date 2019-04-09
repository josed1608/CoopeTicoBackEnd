package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path="/clientes")
public class ClienteControlador {
    private final UsuarioServicio usuarioServicio;
    private final ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(UsuarioServicio usuarioServicio, ClienteServicio clienteServicio) {
        this.usuarioServicio = usuarioServicio;
        this.clienteServicio = clienteServicio;
    }

    @PostMapping()
    public ResponseEntity crearCliente(@RequestBody UsuarioEntidad usuarioEntidad) {
        this.usuarioServicio.agregarUsuario(usuarioEntidad, "Cliente");
        this.clienteServicio.agregarCliente(usuarioEntidad);
        return ok("Cliente agregado");
    }
}
