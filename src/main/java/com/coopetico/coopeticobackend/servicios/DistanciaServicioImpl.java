package com.coopetico.coopeticobackend.servicios;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Servicio para calcular las distancias entre el origen del viaje y los taxistas
 */
@Service
public class DistanciaServicioImpl implements DistanciaServicio {
    /**
     * Da el contexto para poder hacer uso del API de google
     */
    private GeoApiContext apiKey;

    /**
     * Key para usar el API
     */
    private final String API_KEY = "AIzaSyD2MvqQVbfXo3M0mMu4JPGXbaN3y5z9SIg";

    public DistanciaServicioImpl() {
        this.apiKey = new GeoApiContext.Builder()
        .apiKey(API_KEY)
        .build();
    }

    @Override
    public String taxistaMasCercano(LatLng origen, List<Pair<String, LatLng>> taxistas) throws ApiException, InterruptedException, IOException {
        // Crear una lista solo con los puntos longitudinales para poder pasárselo al API
        List<LatLng> ubicacionesTaxistas = new LinkedList<>();
        for (Pair<String, LatLng> taxista : taxistas) {
            ubicacionesTaxistas.add(taxista.getValue());
        }

        // Hacer el request y guardar el resultado
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(apiKey);
        DistanceMatrix result = req.origins(origen)
                .destinations(ubicacionesTaxistas.toArray(new LatLng[0]))
                .mode(TravelMode.DRIVING)
                .units(Unit.METRIC)
                .await();

        long minDistance = Long.MAX_VALUE;
        String taxistaEscogido = "";
        int indiceTaxista = 0;
        // Recorrer la matriz buscando la menor distancia. Como solo hay un origen, solo tendrá una fila
        for (DistanceMatrixElement element : result.rows[0].elements) {
            if (element.distance.inMeters < minDistance){
                minDistance = element.distance.inMeters;
                // El orden de las entradas es el mismo al orden de la lista de taxistas ya que la lista de puntos se hizo con ese orden
                taxistaEscogido = taxistas.get(indiceTaxista).getKey();
            }
            indiceTaxista++;
        }
        return taxistaEscogido;
    }
}
