package com.coopetico.coopeticobackend.controladores;


import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
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
    TaxistasServicio taxistasServicio;

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
        return ubicacionTaxistasServicio.getUbicaciones().keySet().stream()
                .map(correo -> taxistasServicio.consultarTaxistaPorId(correo))
                .filter(Objects::nonNull)
                .map(taxista -> new TaxiTemporal(
                        taxista.getTaxiActual().getPkPlaca(),
                        ((LatLng)ubicacionTaxistasServicio.consultarUbicacionDisponible(taxista.getPkCorreoUsuario())[0]).lat,
                        ((LatLng)ubicacionTaxistasServicio.consultarUbicacionDisponible(taxista.getPkCorreoUsuario())[0]).lng,
                        taxista.getTaxiActual().getClase(),
                        (boolean)ubicacionTaxistasServicio.consultarUbicacionDisponible(taxista.getPkCorreoUsuario())[1],
                        taxista.getTaxiActual().getDatafono()
                        )
                )
                .collect(Collectors.toList());
    }

}
