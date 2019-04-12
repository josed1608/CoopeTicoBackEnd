package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    private final ClientesRepositorio clientesRepositorio;
    private final UsuariosRepositorio usuariosRepositorio;

    @Autowired
    public ClienteServicioImpl(ClientesRepositorio clientesRepositorio, UsuariosRepositorio usuariosRepositorio) {
        this.clientesRepositorio = clientesRepositorio;
        this.usuariosRepositorio = usuariosRepositorio;
    }

    @Override
    public void agregarCliente(UsuarioEntidad usuarioEntidad) throws UsuarioNoEncontradoExcepcion{
        if(!usuariosRepositorio.findById(usuarioEntidad.getPkCorreo()).isPresent())
            throw new UsuarioNoEncontradoExcepcion("No existe el usuario al que se desea asociar el cliente", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        this.clientesRepositorio.save(new ClienteEntidad(usuarioEntidad.getPkCorreo() ,usuarioEntidad, null));
    }

    @Override
    public void borrarCliente(String pkCorreo) throws UsuarioNoEncontradoExcepcion{
        Optional<ClienteEntidad> cliente = clientesRepositorio.findById(pkCorreo);
        if (cliente.isPresent()) {
            clientesRepositorio.delete(cliente.get());
            usuariosRepositorio.delete(cliente.get().getUsuarioByPkCorreoUsuario());
        }
        else
            throw new UsuarioNoEncontradoExcepcion("El usuario que se iba a borrar no existe", HttpStatus.NOT_FOUND, System.currentTimeMillis());
    }
}
