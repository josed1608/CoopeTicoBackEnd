package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la ubicación de los taxistas en tiempo real.
 @author      Marco Venegas
 @since       13-05-2019
 @version     1.0
 */

import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;

public interface UbicacionTaxistasServicio {
    /**
     * Inserta un taxista y su ubicación a la estructura de datos.
     * Si ya existía dentro de la estructura, lo actualiza con la nueva ubicación.
     *
     * @param taxista Par con el id del taxista y la latitud,longitud de su ubicación
     *
     * @author Marco Venegas
     */
    void upsertTaxista(Pair<String, LatLng> taxista);

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
}
