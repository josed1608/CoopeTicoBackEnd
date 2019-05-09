package com.coopetico.coopeticobackend.servicios;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public interface DistanciaServicio {
    /**
     * Devuelve el taxista más cercano al punto de origen
     * @param origen latitud,longitud del punto de origen.
     * @param taxistas Mapa donde el Key es el identificador del taxista y el value es la lat,long de su ubicación.
     * @return retorna el identificador del taxista más cercano
     */
    String taxistaMasCercano(LatLng origen, List<Pair<String, LatLng>> taxistas) throws ApiException, InterruptedException, IOException;
}
