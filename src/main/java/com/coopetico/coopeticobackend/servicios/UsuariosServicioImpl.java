/**
 * Autor: Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 *
 * Esta es la implentación de la intefaz ./UsuarioServicio.java
 * que referencia a la tabla Cliente del ISA Usuario en el ER.
 *
 */
package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuariosServicioImpl implements  UsuariosServicio {

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

    @Override
    @Transactional
    public UsuarioEntidad consultarPorId(String correo){
        UsuarioEntidad resultado = usuarioRepositorio.findById(correo).orElse(null);
        if (clienteRepositorio.findById(correo).orElse(null) == null){
            resultado = null;
        }
        return resultado;
    }

    // @Override
    // @Transactional
    // public void eliminar(String correo){
    //     usuarioRepositorio.deleteById(correo);
    // }

}
