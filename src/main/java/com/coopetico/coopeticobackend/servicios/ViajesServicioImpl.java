//-----------------------------------------------------------------------------
// nombre del packete.
package com.coopetico.coopeticobackend.servicios;
//-----------------------------------------------------------------------------
// Imports.
import com.coopetico.coopeticobackend.entidades.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadPK;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
//-----------------------------------------------------------------------------
// Definición de la clase.
@Service
/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * Fecha: 06/04/2019.
 * <p>
 * Esta es la implentación de la intefaz ./ViajesServicio.java
 */
public class ViajesServicioImpl implements ViajesServicio {
    //-------------------------------------------------------------------------
    // Variables globales
    @Autowired
    private ViajesRepositorio viajesRepositorio;
    private ClienteServicio clientesServicio;
    private TaxisServicio taxisServicio;
    private TaxistasServicio taxistasServicio;
    //-------------------------------------------------------------------------
    // Métodos.
    /**
     * Constructor de la clase. 
     * 
     * @author Joseph Rementería (b55824).
     *  
     * @param vjsRep Repositorio de viajes
     * @param cntSer Servicio de usuarios.
     * @param txsSer Servicio de taxis.
     * @param txstSer Servicio de taxistas.
     */
    public ViajesServicioImpl(
        ViajesRepositorio vjsRep,
        ClienteServicio cntSer,
        TaxisServicio txsSer,
        TaxistasServicio txstSer
    ){
        this.viajesRepositorio = vjsRep;
        this.clientesServicio = cntSer;
        this.taxisServicio = txsSer;
        this.taxistasServicio = txstSer;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 08/04/2019.
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
        // Creación de entidad Viaje per sé.
        ViajeEntidad viajeInsertando = new ViajeEntidad();
        viajeInsertando.setViajeEntidadPK(pk);
        viajeInsertando.setFechaFin(fechaFin);
        viajeInsertando.setCosto(costo);
        viajeInsertando.setEstrellas(estrellas);
        viajeInsertando.setOrigenDestino(origenDestino);
        viajeInsertando.setTaxiByPkPlacaTaxi(
            this.taxisServicio.consultarPorId(placa)
        );
        viajeInsertando.setTaxistaByCorreoTaxi(
                this.taxistasServicio.consultarPorId(correoTaxista)
        );
        viajeInsertando.setClienteByPkCorreoCliente(
                this.clientesServicio
                    .consultarUsuarioPorId(correoCliente)
                    .getClienteByPkCorreo()
        );
        //---------------------------------------------------------------------
        viajeInsertando = viajesRepositorio.save(viajeInsertando);
        return viajeInsertando.toString();
    }
}
