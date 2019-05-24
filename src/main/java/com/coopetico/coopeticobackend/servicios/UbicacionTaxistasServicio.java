package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la ubicación de los taxistas en tiempo real.
 @author      Marco Venegas
 @since       13-05-2019
 @version     1.0
 */

import com.coopetico.coopeticobackend.excepciones.UbicacionNoEncontradaExcepcion;
import com.google.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

public interface UbicacionTaxistasServicio {

    /**
     * Inserta un taxista, su ubicación y su estado disponibilidad a la estructura de datos.
     * Si ya existía dentro de la estructura, lo actualiza con la nueva ubicación y estado de disponibilidad.
     *
     * REQ: No se ingresen nulos.
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
     * REQ: No se ingresen nulos.
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
     * REQ: No se ingresen nulos.
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
     * Consulta la ubicación y disponibilidad actual de un taxista.
     * @param taxistaId Id del taxista que se consultará
     *
     * @return en Object[0] va un objeto LatLng.
     *         en Object[1] va el booleano de disponibilidad.
     *
     * @author Marco Venegas
     */
    Object[] consultarUbicacionDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;

    /**
     * Consulta la ubicación y disponibilidad actual de un taxista.
     * @param taxistaId Id del taxista que se consultará
     *
     * @return en Object[0] va un pair con la lat y la long.
     *         en Object[1] va el booleano de disponibilidad.
     * @author Marco Venegas
     */
    Object[] consultarUbicacionPairDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;

    /**
     * Getter de la estructura de datos
     *
     * @return La estructura de datos.
     *
     * @author Marco Venegas
     */
    HashMap<String, Object[]> getUbicaciones();

    /**
     * Busca los taxistas que estén disponibles en el HashMap
     * @param taxistasQueRechzaron lista con los taxistas que ya dijeron que no a este viaje
     * @return Devuelve una lista con los taxistas que estén disponibles para un viaje
     */
    List<Pair<String, LatLng>> obtenerTaxistasDisponibles(List<String> taxistasQueRechzaron);
}
