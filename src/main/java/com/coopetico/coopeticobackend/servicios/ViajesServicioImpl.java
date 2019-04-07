/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 * <p>
 * Esta es la implentación de la intefaz ./UsuarioServicio.java
 * que referencia a la tabla Cliente del ISA Usuario en el ER.
 */
package com.coopetico.coopeticobackend.servicios;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
public class ViajesServicioImpl implements ViajesServicio {
    /**
     * Variables globales
     */
    @Autowired
    private ViajesRepositorio viajesRepositorio;

    // @Override
    // @Transactional
    // public List<UsuarioEntidad> consultar(){
    //     return usuarioRepositorio.findAll();
    // }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     * <p>
     * Guarda una tupla en la base de datos.
     */
    @Override
    @Transactional
    public String guardar(
            String placa,
            String correo_cliente,
            Timestamp fecha_inicio,
            Timestamp fecha_fin,
            String costo,
            Integer estrellas,
            String origen_destino,
            String correo_taxista
    ) {
        // TODO: crear la entidad.
        return null;//usuarioRepositorio.save(taxista);
    }

    // @Override
    // @Transactional
    // public UsuarioEntidad consultarPorId(String correo){
    //     UsuarioEntidad resultado = usuarioRepositorio.findById(correo).orElse(null);
    //    if (clienteRepositorio.findById(correo).orElse(null) == null){
    //        resultado = null;
    //    }
    //    return resultado;
    // }

    // @Override
    // @Transactional
    // public void eliminar(String correo){
    //     usuarioRepositorio.deleteById(correo);
    // }

}
