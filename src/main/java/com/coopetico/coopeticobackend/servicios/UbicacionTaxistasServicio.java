package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la ubicación de los taxistas en tiempo real.
 @author      Marco Venegas
 @since       13-05-2019
 @version     1.0
 */

import com.coopetico.coopeticobackend.excepciones.UbicacionNoEncontradaExcepcion;
import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;

import java.util.HashMap;

public interface UbicacionTaxistasServicio {

    /**
     * Inserta un taxista, su ubicación y su estado disponibilidad a la estructura de datos.
     * Si ya existía dentro de la estructura, lo actualiza con la nueva ubicación y estado de disponibilidad.
     *
     * @param taxistaId Identificador del taxista que se usa como llave del HashMap
     * @param ubicacion Coordenadas del taxista
     * @param disponible bit que indica si el taxista está disponible para realizar viajes o no.
     *
     * @author Marco Venegas
     */
    void upsertUbicacionDisponibleTaxista(String taxistaId, LatLng ubicacion, Boolean disponible);

    /**
     * Inserta un taxista y su ubicación a la estructura de datos.
     * Si ya existía dentro de la estructura, lo actualiza con la nueva ubicación.
     * Si no existía, asume que está disponible para recibir viajes.
     *
     * @param taxistaId Identificador del taxista que se usa como llave del HashMap
     * @param ubicacion Coordenadas del taxista
     *
     * @author Marco Venegas
     */
    void upsertUbicacionTaxista(String taxistaId, LatLng ubicacion);

    /**
     * Actualiza el estado de disponibilidad de un taxista en la estructura de datos.
     *
     * @param taxistaId Identificador del taxista que se usa como llave del HashMap
     * @param disponible bit que indica si el taxista está disponible para realizar viajes o no.
     *
     * @throws UbicacionNoEncontradaExcepcion si se intenta actualizar el estado de un taxista que no está
     *                                        en la estructura
     *
     * @author Marco Venegas
     */
    void updateDisponibleTaxista(String taxistaId, Boolean disponible) throws UbicacionNoEncontradaExcepcion;

    /**
     * Elimina un taxista y su ubicación a la estructura de datos.
     * @param taxistaId Id del taxista que se eliminará de la estructura
     *
     * @author Marco Venegas
     */
    void eliminarTaxista(String taxistaId);

    /**
     * Consulta la  ubicación actual de un taxista.
     * @param taxistaId Id del taxista que se consultará
     *
     * @author Marco Venegas
     */
    LatLng consultarUbicacion(String taxistaId);

    /**
     * Método adicional que devuelve la latitud y longitud en un Pair.
     *
     * @param taxistaId Id del taxista que se consultará.
     * @return
     */
    Pair<Double, Double> consultarUbicacionPair(String taxistaId);

    /**
     * Consulta la  disponibilidad actual de un taxista.
     * @param taxistaId Id del taxista que se consultará
     *
     * @author Marco Venegas
     */
    Boolean consultarDisponible(String taxistaId);


    HashMap<String, Object[]> getUbicaciones();
}
