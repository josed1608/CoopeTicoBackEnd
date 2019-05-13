package com.coopetico.coopeticobackend.controladores;


import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    HashMap<String, Double> ubicacionTaxis;

    @Autowired
    TaxisServicio taxisServicio;

    /**
     * Metodo para obtener una lista de taxis
     * @return Lista de taxis
     */
    @GetMapping("/taxis")
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
        if (primero) {
            taxis.add(new TaxiTemporal("AAA111", 22.33159, 105.63233, "A", true, true));
            taxis.add(new TaxiTemporal("AAA111", 7.92658, -12.05228, "B", true, true));
            taxis.add(new TaxiTemporal("AAA111", 48.75606, -118.85900, "C", false, true));

        } else{
            taxis.add(new TaxiTemporal("AAA111", 5.19334,  -67.03352,  "A", true,  true));
            taxis.add(new TaxiTemporal("AAA111", 12.09407, 26.31618,   "B", false, true));
            taxis.add(new TaxiTemporal("AAA111", 47.92393, 78.58339,   "C", true,  true));

        }
        primero = !primero;
        return taxis;
    }


    /**
     * Metodo que se encarga de asociar cada taxi de la estructura de las ubicaciones de los taxis
     * con una clase de TaxiTemporal.
     * @return Lista TaxiTemporal con sus caracteristicas necesarias para el FE
     */
    public List<TaxiTemporal> asociarTaxis(){
        List<TaxiTemporal> listaUbicacionTaxi = new ArrayList<>();
        List<TaxiEntidad> listaTaxis = taxisServicio.consultar();
        Set<String> ubicacionesTaxi = ubicacionTaxis.keySet();
        for (String placa : ubicacionesTaxi) {
            // TODO Se necesita realizar una clase para
            // TODO usar estructura de Marco
            TaxiEntidad taxi = obtenerTaxi(listaTaxis, placa);
            listaUbicacionTaxi.add(new TaxiTemporal(placa,ubicacionTaxis.get(placa), ubicacionTaxis.get(placa), taxi.getClase(),  true, taxi.getDatafono()));
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
