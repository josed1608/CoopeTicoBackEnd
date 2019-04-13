package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    private final ClientesRepositorio clientesRepositorio;
    private final UsuariosRepositorio usuarioRepositorio;

    @Autowired
    public ClienteServicioImpl(
        ClientesRepositorio clientesRepositorio,
        UsuariosRepositorio usrRep
    ) {
        this.clientesRepositorio = clientesRepositorio;
        this.usuarioRepositorio = usrRep;
    }

    @Override
    public void agregarCliente(UsuarioEntidad usuarioEntidad) {
        this.clientesRepositorio.save(new ClienteEntidad(usuarioEntidad.getPkCorreo() ,usuarioEntidad, null));
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
    public UsuarioEntidad consultarPorId(String correo){
        UsuarioEntidad resultado = usuarioRepositorio.findById(correo)
                .orElse(null);
        if (clientesRepositorio.findById(correo).orElse(null) == null){
            resultado = null;
        }
        return resultado;
    }
}
