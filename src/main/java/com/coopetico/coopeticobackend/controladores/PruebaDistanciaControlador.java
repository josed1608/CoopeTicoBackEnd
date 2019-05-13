package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.servicios.DistanciaServicio;
import com.google.maps.model.LatLng;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador para probar que el servicio de distanncia funcione de manera esperada
 */
@RestController
@RequestMapping("/dist")
public class PruebaDistanciaControlador {
    @Autowired
    private DistanciaServicio distanciaServicio;

    /**
     * Tiene un destino y un destino quemados, el taxi1 debería reconocerse como el más cercano
     * @return el id del taxista más cercano
     */
    @GetMapping("/test")
    public ResponseEntity testDistancia() {
        LatLng origen  = new LatLng(9.963621, -84.067743);
        LatLng destino1 = new LatLng(9.963144, -84.054909);
        LatLng destino2 = new LatLng(9.957288, -84.039617);

        List<Pair<String, LatLng>> taxistas = new LinkedList<>();
        taxistas.add(new Pair<>("taxi2", destino2));
        taxistas.add(new Pair<>("taxi1", destino1));

        try {
            return ok(distanciaServicio.taxistaMasCercano(origen, taxistas));
        }
        catch (Exception e) {
            return notFound().build();
        }
    }
}
