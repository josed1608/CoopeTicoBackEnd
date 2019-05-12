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
 * Esta es la implentación de la intefaz ./ViajesServicio.java
 *
 * @author Joseph Rementería (b55824)
 * @since 06-04-2019
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
     * Guarda una tupla en la base de datos.
     *
     * @author Joseph Rementería (b55824)
     * @since 08-04-2019
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
                this.taxistasServicio.taxistaRepositorio.
                    findById(correoTaxista).orElse(null)
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
     * @author Joseph Rementería (b55824)
     * @since 11-05-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje
     * @param correoUsuario el correo del cliente o la operadora que
     *                      solicita el viaje
     * @param origen el punto de origen del viaje
     * @param correoTaxista el correo del taxista asignado al viaje
     *
     * @return  0 si no hubo problemas,
     *          -1 si hubo un problema no manejado.
     *          -2 si el usuario no es ni cliente ni operador,
     */
    @Override
    @Transactional
    public int crear (
        String placa,
        Timestamp fechaInicio,
        String correoUsuario,
        String origen,
        String correoTaxista
    ) {
        int result = 0;
        try {
            //-----------------------------------------------------------------
            // Creación de la llave primaria para la entidad Viaje.
            ViajeEntidadPK pk = new ViajeEntidadPK();
            pk.setPkPlacaTaxi(placa);
            pk.setPkFechaInicio(fechaInicio);
            //-----------------------------------------------------------------
            //-----------------------------------------------------------------
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
            //-----------------------------------------------------------------
            // Acá se referencia sea el cliente o el operador con el viaje.
            ClienteEntidad clienteCreador = this.clientesServicio
                    .consultarUsuarioPorId(correoUsuario)
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
                } else {
                    return -2;
                }
            }
            //-----------------------------------------------------------------
            viajeEnCreacion = viajesRepositorio.save(viajeEnCreacion);;
        } catch (Exception e) {
            result = -1;
        }
        return result;
        //---------------------------------------------------------------------
    }
    //-------------------------------------------------------------------------
}
//-----------------------------------------------------------------------------