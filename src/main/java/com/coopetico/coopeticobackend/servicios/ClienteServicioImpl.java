package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    private final ClientesRepositorio clientesRepositorio;

    @Autowired
    public ClienteServicioImpl(ClientesRepositorio clientesRepositorio) {
        this.clientesRepositorio = clientesRepositorio;
    }

    @Override
    public void agregarCliente(UsuarioEntidad usuarioEntidad) {
        this.clientesRepositorio.save(new ClienteEntidad(usuarioEntidad.getPkCorreo() ,usuarioEntidad, null));
    }
}
