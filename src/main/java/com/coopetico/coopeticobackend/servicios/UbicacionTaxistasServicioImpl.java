package com.coopetico.coopeticobackend.servicios;

/**
 Servicio que contiene la estructura de datos de la ubicación de los taxistas en tiempo real.
 @author      Marco Venegas
 @since       13-05-2019
 @version:    1.0
 */

import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UbicacionTaxistasServicioImpl implements UbicacionTaxistasServicio {

    /**
     * Estructura de datos que contiene como llave el correo de los taxistas
     * y como valores las ubicaciones de dichos taxistas.
     */
    private HashMap<String, LatLng> ubicaciones;

    public UbicacionTaxistasServicioImpl(){
        this.ubicaciones = new HashMap<>(); //Inicializa la estructura de datos
    }

    @Override
    public void upsertTaxista(Pair<String, LatLng> taxista) {
        ubicaciones.put(taxista.getFirst(), taxista.getSecond());
    }

    @Override
    public void eliminarTaxista(String taxistaId) {
        ubicaciones.remove(taxistaId);
    }

    @Override
    public LatLng consultarUbicacion(String taxistaId) {
        return ubicaciones.get(taxistaId);
    }
}
