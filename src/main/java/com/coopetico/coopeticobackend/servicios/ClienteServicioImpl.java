package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    private final ClientesRepositorio clientesRepositorio;
    private final UsuariosRepositorio usuariosRepositorio;

    @Autowired
    public ClienteServicioImpl(
        ClientesRepositorio clientesRepositorio,
        UsuariosRepositorio usrRep
    ) {
        this.clientesRepositorio = clientesRepositorio;
        this.usuariosRepositorio = usrRep;
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

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Trae una Entidad usuario que corresponde al correo ingresado.
     *
     * @param correo el correo a consultar.
     * @return UsuarioEntidad del correo en la base, null de otra manera.
     */
    @Override
    @Transactional
    public UsuarioEntidad consultarUsuarioPorId(String correo){
        UsuarioEntidad resultado = usuariosRepositorio.findById(correo)
                .orElse(null);
        if (clientesRepositorio.findById(correo).orElse(null) == null){
            resultado = null;
        }
        return resultado;
    }
    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 19/04/2019.
     *
     * Trae una Entidad usuario que corresponde al correo ingresado.
     *
     * @param correo el correo a consultar.
     * @return UsuarioEntidad del correo en la base, null de otra manera.
     */
    @Override
    @Transactional
    public ClienteEntidad consultarClientePorId(String correo){
        return clientesRepositorio.findById(correo).orElse(null);
    }

    @Override
    public void modificarCliente(UsuarioEntidad usuarioEntidad, String correo) throws UsuarioNoEncontradoExcepcion{
        if(!usuariosRepositorio.findById(usuarioEntidad.getPkCorreo()).isPresent())
            throw new UsuarioNoEncontradoExcepcion("No existe el usuario al que se desea asociar el cliente", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        // update el cliente
    }
}
