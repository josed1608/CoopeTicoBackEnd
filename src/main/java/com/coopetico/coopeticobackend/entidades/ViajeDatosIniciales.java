//-----------------------------------------------------------------------------
// Packete.
package com.coopetico.coopeticobackend.entidades;
//-----------------------------------------------------------------------------
// Imports.
import java.sql.Timestamp;
//-----------------------------------------------------------------------------
/**
 * Clase temporal previa a la creación de un viaje en la base de datos
 * viaje en la base de datos.
 *
 *
 * @author Joseph Rementería (b55824)
 * @since 10-05-2019
 * @version 1.0
 *
 */
public class ViajeDatosIniciales {
    //-------------------------------------------------------------------------
    // Variables globales
    private String placa;
    private String correoCliente;
    private String fechaInicio;
    private String origen;
    private String destino;
    private String correoTaxista;
    //-------------------------------------------------------------------------
    // Constructor
    /**
     *
     * Constructor con todos los parámetros
     *
     * @param placa id del carro.
     * @param correoCliente correo del pasajero.
     * @param fechaInicio fecha de inicio del viaje.
     * @param origen punto de origen y destino.
     * @param correoTaxista correo del conductor.
     */
    public ViajeDatosIniciales(
        String placa,
        String correoCliente,
        String fechaInicio,
        String origen,
        String destino,
        String correoTaxista
    ) {
        this.placa = placa;
        this.correoCliente = correoCliente;
        this.fechaInicio = fechaInicio;
        this.origen = origen;
        this.destino = destino;
        this.correoTaxista = correoTaxista;
    }

    public ViajeDatosIniciales() {
    }
    //-------------------------------------------------------------------------
    // setters and getter autogenerated by MS VSC (Windows version).
    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the correoCliente
     */
    public String getCorreoCliente() {
        return correoCliente;
    }

    /**
     * @param correoCliente the correoCliente to set
     */
    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    /**
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origenDestino the origen to set
     */
    public void setOrigen(String origenDestino) {
        this.origen = origenDestino;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the correoTaxista
     */
    public String getCorreoTaxista() {
        return correoTaxista;
    }

    /**
     * @param correoTaxista the correoTaxista to set
     */
    public void setCorreoTaxista(String correoTaxista) {
        this.correoTaxista = correoTaxista;
    }
    //-------------------------------------------------------------------------
}
//-------------------------------------------------------------------------