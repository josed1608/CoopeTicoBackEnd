package com.coopetico.coopeticobackend.servicios;

/**
 Servicio que contiene la estructura de datos de la ubicación de los taxistas en tiempo real.
 @author      Marco Venegas
 @since       13-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.excepciones.UbicacionNoEncontradaExcepcion;
import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UbicacionTaxistasServicioImpl implements UbicacionTaxistasServicio {

    /**
     * Estructura de datos que contiene como llave el correo de los taxistas
     * y como valores las ubicaciones de dichos taxistas.
     */
    private HashMap<String, Object[]> ubicaciones;

    public UbicacionTaxistasServicioImpl(){
        this.ubicaciones = new HashMap<>(); //Inicializa la estructura de datos
    }

    @Override
    public void upsertUbicacionDisponibleTaxista(String taxistaId, LatLng ubicacion, Boolean disponible) {
        Object[] valores = new Object[2];
        valores[0] = ubicacion;
        valores[1] = disponible;
        ubicaciones.put(taxistaId, valores);
    }

    @Override
    public void upsertUbicacionTaxista(String taxistaId, LatLng ubicacion){
        Object[] valores = new Object[2];
        valores[0] = ubicacion;

        if(ubicaciones.containsKey(taxistaId)){ //Si ya existe en la estructura
            boolean disponible = (boolean)ubicaciones.get(taxistaId)[1]; //Obtengo el valor actual de dispobible
            valores[1] = disponible;
        }else{
            valores[1] = true; //Se settea como true por default.
        }
        ubicaciones.put(taxistaId, valores);
    }

    @Override
    public void updateDisponibleTaxista(String taxistaId, Boolean disponible) throws UbicacionNoEncontradaExcepcion {
        Object[] valores = new Object[2];
        valores[1] = disponible;

        if(ubicaciones.containsKey(taxistaId)){ //Si ya existe en la estructura
            LatLng ubicacion = (LatLng) ubicaciones.get(taxistaId)[0]; //Obtengo el valor actual de la ubicacion
            valores[0] = ubicacion;
        }else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
        ubicaciones.put(taxistaId, valores);
    }

    @Override
    public void eliminarTaxista(String taxistaId) {
        ubicaciones.remove(taxistaId);
    }

    @Override
    public LatLng consultarUbicacion(String taxistaId) throws UbicacionNoEncontradaExcepcion{
        if(ubicaciones.containsKey(taxistaId)){ //Verifica que exista para que no intente accesar al índice de un null
            return (LatLng)ubicaciones.get(taxistaId)[0];
        }
        else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public Pair<Double, Double> consultarUbicacionPair(String taxistaId) throws UbicacionNoEncontradaExcepcion {
        if(ubicaciones.containsKey(taxistaId)){
            LatLng ubicacion = (LatLng)ubicaciones.get(taxistaId)[0];
            String ubicacionHilera = ubicacion.toString();
            double latitud = Double.parseDouble(ubicacionHilera.split(",")[0]);
            double longitud = Double.parseDouble(ubicacionHilera.split(",")[1]);
            return Pair.of(latitud, longitud);
        } else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public Boolean consultarDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion {
        if(ubicaciones.containsKey(taxistaId)){
            return (boolean)ubicaciones.get(taxistaId)[1];
        } else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public Object[] consultarUbicacionDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion{
        if(ubicaciones.containsKey(taxistaId)){
            return ubicaciones.get(taxistaId);
        } else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public Object[] consultarUbicacionPairDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion{
        if(ubicaciones.containsKey(taxistaId)){
            Object[] datos = new Object[2];

            LatLng ubicacion = (LatLng)ubicaciones.get(taxistaId)[0];
            boolean disponible = (Boolean)ubicaciones.get(taxistaId)[1];

            String ubicacionHilera = ubicacion.toString();
            double latitud = Double.parseDouble(ubicacionHilera.split(",")[0]);
            double longitud = Double.parseDouble(ubicacionHilera.split(",")[1]);

            datos[0] = Pair.of(latitud, longitud);
            datos[1] = disponible;

            return datos;
        } else{
            throw new UbicacionNoEncontradaExcepcion("No se encontró el taxista", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public HashMap<String, Object[]> getUbicaciones(){
        return this.ubicaciones;
    }

    @Override
    public List<Pair<String, LatLng>> obtenerTaxistasDisponibles(List<String> taxistasQueRechazaron) {
        return ubicaciones.entrySet().stream()
                .filter(entry -> (boolean)entry.getValue()[1])
                .filter(taxista -> !taxistasQueRechazaron.contains(taxista.getKey()))
                .map(entry -> Pair.of(entry.getKey(), (LatLng)entry.getValue()[0]))
                .collect(Collectors.toList());
    }
}
