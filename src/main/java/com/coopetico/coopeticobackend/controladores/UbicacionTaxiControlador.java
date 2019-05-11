package com.coopetico.coopeticobackend.controladores;


import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/ubicacion")
@Validated
public class UbicacionTaxiControlador {
    boolean primero = true;

    /**
     * Metodo para obtener una lista de usuarios
     * @return Lista de usuarios
     */
    @GetMapping("/taxis")
    public List<TaxiTemporal> obtenerUsuarios(){
        List<TaxiTemporal> taxis = getTaxis();
        return taxis;
    }


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
}
