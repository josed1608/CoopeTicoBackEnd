package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public interface TaxisServicio {
    @CrossOrigin(origins = "http://localhost:4200")
    List<TaxiEntidad> consultar();
    TaxiEntidad consultarPorId(String placa);
    TaxiEntidad guardar(TaxiEntidad taxi);
    void eliminar(String placa);
}