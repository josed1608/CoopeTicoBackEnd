package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;

import java.util.List;

public interface TaxisServicio {
    List<TaxiEntidad> consultar();
    TaxiEntidad consultarPorId(String placa);
    TaxiEntidad guardar(TaxiEntidad taxi);
    void eliminar(String placa);
}