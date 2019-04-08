/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 *
 * Esta es la implentación de la intefaz ./UsuarioServicio.java
 * que referencia a la tabla Cliente del ISA Usuario en el ER.
 *
 */
package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuariosServicioImpl implements  UsuariosServicio {
    /**
     * Variables globales.
     */
    @Autowired
    private UsuariosRepositorio usuarioRepositorio;

    @Autowired
    private ClientesRepositorio clienteRepositorio;

    // @Override
    // @Transactional
    // public List<UsuarioEntidad> consultar(){
    //     return usuarioRepositorio.findAll();
    // }

    // @Override
    // @Transactional
    // public UsuarioEntidad guardar(UsuarioEntidad taxista){
    //     return usuarioRepositorio.save(taxista);
    // }

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
        if (clienteRepositorio.findById(correo).orElse(null) == null){
            resultado = null;
        }
        return resultado;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 08/04/2019.
     *
     * Trae una Entidad Cliente que corresponde al correo ingresado.
     *
     * @param correo el correo a consultar.
     * @return ClienteEntidad del correo en la base, null de otra manera.
     */
    @Override
    @Transactional
    public ClienteEntidad consultarClientePorId(String correo){
        return clienteRepositorio.findById(correo).orElse(null);
    }

    // @Override
    // @Transactional
    // public void eliminar(String correo){
    //     usuarioRepositorio.deleteById(correo);
    // }

}
