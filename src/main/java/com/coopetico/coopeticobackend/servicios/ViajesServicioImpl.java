/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 * <p>
 * Esta es la implentación de la intefaz ./UsuarioServicio.java
 * que referencia a la tabla Cliente del ISA Usuario en el ER.
 */
package com.coopetico.coopeticobackend.servicios;
import com.coopetico.coopeticobackend.entidades.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadPK;
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
            String correoCliente,
            Timestamp fechaInicio,
            Timestamp fechaFin,
            String costo,
            Integer estrellas,
            String origenDestino,
            String correoTaxista
    ) {
        //---------------------------------------------------------------------
        // Creación de la llave primaria para la entidad Vaije.
        ViajeEntidadPK pk = new ViajeEntidadPK();
        pk.setPkCorreoCliente(correoCliente);
        pk.setPkPlacaTaxi(placa);
        pk.setPkFechaInicio(fechaInicio);
        //---------------------------------------------------------------------
        // Obtención de la Entidad Taxi para a partir de la placa.
        // TODO: taxiEntidad = taxiServico.consultarPorId(placa);
        //---------------------------------------------------------------------
        // Obtención de la Entidad Taxista para a partir del correo.
        // TODO: taxistaEntidad = taxistaServico.consultarPorId(correoTaxista);
        //---------------------------------------------------------------------
        // Obtención de la Entidad Cliente para a partir del correo.
        // TODO: clienteEntidad = usuarioServico.consultarPorId(correoCliente);
        //---------------------------------------------------------------------
        // Creación de entidad Viaje per sé.
        ViajeEntidad viajeInsertando = new ViajeEntidad();
        viajeInsertando.setViajeEntidadPK(pk);
        viajeInsertando.setFechaFin(fechaFin);
        viajeInsertando.setCosto(costo);
        viajeInsertando.setEstrellas(estrellas);
        viajeInsertando.setOrigenDestino(origenDestino);
        // TODO: viajeInsertando.setTaxiByPkPlacaTaxi(taxiEntidad);
        // TODO: viajeInsertando.setTaxistaByCorreoTaxi(taxistaEntidad);
        // TODO: viajeInsertando.setClienteByPkCorreoCliente(clienteEntidad);
        //---------------------------------------------------------------------
        viajeInsertando = viajesRepositorio.save(viajeInsertando);
        return viajeInsertando.toString();//usuarioRepositorio.save(taxista);
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
