package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.CorreoTomadoExcepcion;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador para la entidad Cliente
 */
@RestController
@RequestMapping(path="/clientes")
public class ClienteControlador {
    private final UsuarioServicio usuarioServicio;
    private final ClienteServicio clienteServicio;
    private final String grupoPorDefectoClientes = "Cliente";

    @Autowired
    public ClienteControlador(UsuarioServicio usuarioServicio, ClienteServicio clienteServicio) {
        this.usuarioServicio = usuarioServicio;
        this.clienteServicio = clienteServicio;
    }

    /**
     * Crea un nuevo cliente con la información de usuario que se le envía
     * @param usuarioEntidad Entidad de usuario con la información del cliente por crear
     * @return retorna ok si se crea el usuario correctamente
     * @throws CorreoTomadoExcepcion Si el usuario ya existe
     * @throws GrupoNoExisteExcepcion si el grupo Cliente no existe en la base de datos
     * @author      Jose David Vargas Artavia
     */
    @PostMapping()
    public ResponseEntity crearCliente(@RequestBody UsuarioEntidad usuarioEntidad) throws CorreoTomadoExcepcion, GrupoNoExisteExcepcion {
        this.usuarioServicio.agregarUsuario(usuarioEntidad, this.grupoPorDefectoClientes);
        this.clienteServicio.agregarCliente(usuarioEntidad);
        return ok("Cliente agregado");
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
    @GetMapping(path = "/obtenerUsuario/{correo}")
    public String obtenerUsuario(@PathVariable String correo) {
        try {
            return this.clienteServicio.consultarUsuarioPorId(correo).toString();
        } catch (Exception e) {
            return "Not found";
        }
    }
}
