//-----------------------------------------------------------------------------
// nombre del packete.
package com.coopetico.coopeticobackend.servicios;
//-----------------------------------------------------------------------------
// Imports.
import com.coopetico.coopeticobackend.entidades.bd.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.bd.OperadorEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
        String fechaInicio,
        String fechaFin,
        String costo,
        Integer estrellas,
        String origen,
        String destino,
        String correoTaxista
    ) {
        //---------------------------------------------------------------------
        // Creación de la llave primaria para la entidad Viaje.
        ViajeEntidadPK pk = new ViajeEntidadPK();
        //pk.setPkCorreoCliente(correoCliente);
        pk.setPkPlacaTaxi(placa);
        pk.setPkFechaInicio(fechaInicio);//Timestamp.valueOf(fechaInicio));
        //---------------------------------------------------------------------
        // Creación de entidad Viaje per sé.
        ViajeEntidad viajeInsertando = new ViajeEntidad();
        viajeInsertando.setViajeEntidadPK(pk);
        viajeInsertando.setFechaFin(fechaFin);//Timestamp.valueOf(fechaFin));
        viajeInsertando.setCosto(costo);
        viajeInsertando.setEstrellas(estrellas);
        viajeInsertando.setOrigen(origen);
        viajeInsertando.setDestino(destino);
        viajeInsertando.setTaxiByPkPlacaTaxi(
            this.taxisServicio.consultarPorId(placa)
        );
        viajeInsertando.setTaxistaByCorreoTaxi(
                this.taxistasServicio.consultarTaxistaPorId(correoTaxista)
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
     * Método para consultar todos los viajes a la base
     * @return Lista de viajes
     */
    @Override
    @Transactional
    public List<ViajeEntidad> consultarViajes() {
        return viajesRepositorio.findAll();
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
     * @param fechaInicio la fecha de inicio de un viaje en el formato "yyyy-mm-dd hh:mm:ss"
     * @param correoUsuario el correo del cliente o la operadora que
     *                      solicita el viaje
     * @param origen el punto de origen del viaje
     * @param correoTaxista el correo del taxista asignado al viaje
     *
     * @return  0 si no hubo problemas,
     *          -1 si hubo un problema no manejado.
     *          -2 si el usuario no es ni cliente ni operador,
     *          -3 si no se pudo insertar el viaje en la base de datos,
     *          -4 si el viaje ya ha sido previamente insertado en la DB
     */
    @Override
    @Transactional
    public int crear (
        String placa,
        String fechaInicio,
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
            pk.setPkFechaInicio(fechaInicio);//Timestamp.valueOf(fechaInicio));
            //-----------------------------------------------------------------
            //-----------------------------------------------------------------
            // Creación de entidad Viaje per sé.
            ViajeEntidad viajeEnCreacion = new ViajeEntidad();
            viajeEnCreacion.setViajeEntidadPK(pk);
            viajeEnCreacion.setTaxiByPkPlacaTaxi(
                    this.taxisServicio.consultarPorId(placa)
            );
            viajeEnCreacion.setTaxistaByCorreoTaxi(
                this.taxistasServicio.
                    consultarTaxistaPorId(correoTaxista)
            );
            viajeEnCreacion.setOrigen(origen);
            //-----------------------------------------------------------------
            // Acá se referencia sea el cliente o el operador con el viaje.
            ClienteEntidad clienteCreador = this.clientesServicio
                    .consultarClientePorId(correoUsuario);
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
            // Si el viaje está en la base de datos entonces turra un error.
            if (this.viajesRepositorio.existsById(pk)){
                result = -4;
            } else {
                //-------------------------------------------------------------
                try {
                    //---------------------------------------------------------
                    // Se inenta insertar enla base de datos.
                    viajeEnCreacion = viajesRepositorio.save(viajeEnCreacion);
                    //---------------------------------------------------------
                } catch (Exception e) {
                    result = -3;
                }
                //-------------------------------------------------------------
            }
            //-----------------------------------------------------------------
        } catch (Exception e) {
            result = -1;
        }
        return result;
        //---------------------------------------------------------------------
    }

    /**
     * Este es el método a usar para actualizar la fecha de finalización de un viaje.
     *
     * @author Marco Venegas (B67697)
     * @since 27-05-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje en el formato "yyyy-mm-dd hh:mm:ss"
     * @param fechaFin la fecha en la que finalizó el viaje en el formato "yyyy-mm-dd hh:mm:ss"
     *
     * @return Int con el estado  0 si se actualizó correctamente
     *                           -1 si hubo un problema no manejado.
     *                           -2 si no existe ese viaje en la bd.
     *                           -3 No se puede finalizar un viaje que ya finalizó.
     *                           -4 Formato de fecha inválido
     *                           -5 la fecha de finalización no puede ser anterior a la fecha de inicio
     *                           -6 si no se pudo guardar el cambio en la bd.
     */
    public int finalizar(String placa, String fechaInicio, String fechaFin){
        try{
            ViajeEntidad viajeAFinalizar = viajesRepositorio.encontrarViaje(placa, fechaInicio);
            if(viajeAFinalizar == null){
                return -2;
            }
            if(viajeAFinalizar.getFechaFin() != null){
                return -3;
            }
            if (fechaFin.length() != 19) {
                return -4;
            }
            if(Timestamp.valueOf(fechaFin).before(Timestamp.valueOf(fechaInicio))){
                return -5;
            }

            viajeAFinalizar.setFechaFin(fechaFin);//Timestamp.valueOf(fechaFin));
            try{
                viajesRepositorio.save(viajeAFinalizar);
            }catch(Exception e){
                return -6;
            }
        }catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * Este es el método a usar para actualizar la estrellas de un viaje.
     *
     * @author Marco Venegas (B67697)
     * @since 22-06-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje en el formato "yyyy-mm-dd hh:mm:ss"
     * @param estrellas la cantidad de estrellas con las que se calificó el viaje.
     *
     * @return Int con el estado  0 si se actualizó correctamente
     *                           -1 si hubo un problema no manejado.
     *                           -2 si no existe ese viaje en la bd.
     *                           -3 No se puede finalizar un viaje que no ha finalizado.
     *                           -4 No se pueden asignar menos de 1 ni más de 5 estrellas.
     *                           -5 si no se pudo guardar el cambio en la bd.
     */
    public int asignarEstrellas(String placa, String fechaInicio, int estrellas){
        try{
            ViajeEntidad viajeACalificar = viajesRepositorio.encontrarViaje(placa, fechaInicio);
            if(viajeACalificar == null){
                return -2;
            }
            if(viajeACalificar.getFechaFin() == null){
                return -3;
            }
            if (estrellas < 1 || estrellas > 5) {
                return -4;
            }

            viajeACalificar.setEstrellas(estrellas);
            try{
                viajesRepositorio.save(viajeACalificar);
            }catch(Exception e){
                return -5;
            }
        }catch (Exception e) {
            return -1;
        }
        return 0;
    }
}
//-----------------------------------------------------------------------------