package com.coopetico.coopeticobackend.controladores;


import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/ubicacion")
@Validated
/**
 * Clase encargada de manejar las ubicaciones de los taxis
 */
public class UbicacionTaxiControlador {
    // Boolena para simular que los taxis se mueven
    boolean primero = true;

    // Booleano para simular
    boolean simular = false;

    @Autowired
    TaxisServicio taxisServicio;

    @Autowired
    UbicacionTaxistasServicio ubicacionTaxistasServicio;

    private final int LOCALIZACION = 0;
    private final int ESTA_DISPONIBLE = 1;


    /**
     * Metodo para obtener una lista de taxis
     * @return Lista de taxis
     */
    @GetMapping("/taxis")
    @ResponseStatus(HttpStatus.OK)
    public List<TaxiTemporal> obtenerTaxis(){
        List<TaxiTemporal> taxis = getTaxis();

        return taxis;
    }


    /**
     * Metodo que simula las distintas posiciones de un taxi
     * @return Informacion de los taxis
     */
    public List<TaxiTemporal> getTaxis(){
        List<TaxiTemporal> taxis = new ArrayList<>();

        HashMap<String, Object[]> ubicacionTaxis = ubicacionTaxistasServicio.getUbicaciones();
        List<TaxiEntidad> taxisEntidad = taxisServicio.consultar();

        taxis = this.asociarTaxis(ubicacionTaxis, taxisEntidad);

        if(simular) {
            if (primero) {
                taxis.add(new TaxiTemporal("AAA111", 22.33159, 105.63233, "A", true, true));
                taxis.add(new TaxiTemporal("AAA111", 7.92658, -12.05228, "B", true, true));
                taxis.add(new TaxiTemporal("AAA111", 48.75606, -118.85900, "C", false, true));

            } else {
                taxis.add(new TaxiTemporal("AAA111", 5.19334, -67.03352, "A", true, true));
                taxis.add(new TaxiTemporal("AAA111", 12.09407, 26.31618, "B", false, true));
                taxis.add(new TaxiTemporal("AAA111", 47.92393, 78.58339, "C", true, true));

            }
        }
        primero = !primero;
        return taxis;
    }


    /**
     * Metodo que se encarga de asociar cada taxi de la estructura de las ubicaciones de los taxis
     * con una clase de TaxiTemporal.
     * @return Lista TaxiTemporal con sus caracteristicas necesarias para el FE
     */
    public List<TaxiTemporal> asociarTaxis(HashMap<String, Object[]> ubicacionesTaxis, List<TaxiEntidad> listaTaxis ){
        // Lista a retornar
        List<TaxiTemporal> listaUbicacionTaxi = new ArrayList<>();
        // Se obtienen las llaves de la estructura
        Set<String> placasTaxi = ubicacionesTaxis.keySet();
        for (String placa : placasTaxi) {
            // Se obtiene correspondiente a la placa
            TaxiEntidad taxi = obtenerTaxi(listaTaxis, placa);
            // Valor según la llave (placa) dada
            Object[] datosEstructura = ubicacionesTaxis.get(placa);
            // Se obtiene la ubicacion de los taxis
            LatLng ubicacion = (LatLng) datosEstructura[LOCALIZACION];
            // Booleano con la disponibilidad de los taxis
            boolean disponible = (boolean) datosEstructura[ESTA_DISPONIBLE];
            // Se añade nuevo taxista a la lista
            listaUbicacionTaxi.add(new TaxiTemporal(placa, ubicacion.lat , ubicacion.lng, taxi.getClase(),  disponible, taxi.getDatafono()));
        }
        return  listaUbicacionTaxi;
    }

    /**
     * Metodo que obtiene el taxi de una lista
     * @param taxis Lista de taxis
     * @param placa Placa a buscar
     * @return El objeto del taxi
     */
    public TaxiEntidad obtenerTaxi(List<TaxiEntidad> taxis, String placa){
        for (TaxiEntidad taxi : taxis) {
            if(taxi.getPkPlaca().equals(placa))
                return taxi;
        }
        return null;
    }

}
