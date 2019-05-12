//-----------------------------------------------------------------------------
// nombre del packete.
package com.coopetico.coopeticobackend.servicios;
//-----------------------------------------------------------------------------
// Imports.
import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.OperadorEntidad;
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
    private TaxistasServicioImpl taxistasServicio;
    private OperadoresServicio operadorServicio;
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
        TaxistasServicioImpl txstSer
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
        // Creación de la llave primaria para la entidad Viaje.
        ViajeEntidadPK pk = new ViajeEntidadPK();
        //pk.setPkCorreoCliente(correoCliente);
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
                this.taxistasServicio.taxistaRepositorio.findById(correoTaxista).orElse(null)
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

    /**
     * Este es el método a usar para crear un viaje en el sistema, solo con
     * los datos iniciales disponibles en el momento en que se cominza un
     * viaje por parte del taxista.
     *
     * Fecha: 11/05/2019
     *
     * @author Joseph Rementería (b55824)
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje
     * @param correoUsuario el correo del cliente o la operadora que solicita el viaje
     * @param origen el punto de origen del viaje
     * @param correoTaxista el correo del taxista asignado al viaje
     *
     * @return una string (TODO: find out why? xd)
     */
    @Override
    @Transactional
    public String crear (
        String placa,
        Timestamp fechaInicio,
        String correoUsuario,
        String origen,
        String correoTaxista
    ) {
        //---------------------------------------------------------------------
        // Creación de la llave primaria para la entidad Viaje.
        ViajeEntidadPK pk = new ViajeEntidadPK();
        pk.setPkPlacaTaxi(placa);
        pk.setPkFechaInicio(fechaInicio);
        //---------------------------------------------------------------------
        //---------------------------------------------------------------------
        // Creación de entidad Viaje per sé.
        ViajeEntidad viajeEnCreacion = new ViajeEntidad();
        viajeEnCreacion.setViajeEntidadPK(pk);
        viajeEnCreacion.setTaxiByPkPlacaTaxi(
                this.taxisServicio.consultarPorId(placa)
        );
        viajeEnCreacion.setTaxistaByCorreoTaxi(
                this.taxistasServicio.taxistaRepositorio.
                        findById(correoTaxista).orElse(null)
        );
        //---------------------------------------------------------------------
        ClienteEntidad clienteCreador = this.clientesServicio
            .consultarUsuarioPorId(correoUsuario)
            //.consultarUsuarioPorId("this_is_not_an_email@nomail.com")
            // TODO: test this to see whether the next structure is accurate
            .getClienteByPkCorreo();
        if (clienteCreador != null) {
            viajeEnCreacion.setClienteByPkCorreoCliente(
                clienteCreador
            );
        } else {
            OperadorEntidad operadorCreador = this.operadorServicio
                .consultarPorId(correoUsuario);
            if (operadorCreador != null) {
                viajeEnCreacion.setAgendaOperador(operadorCreador);
            }
        }
        //---------------------------------------------------------------------
        viajeEnCreacion = viajesRepositorio.save(viajeEnCreacion);
        return viajeEnCreacion.toString();
    }
}
