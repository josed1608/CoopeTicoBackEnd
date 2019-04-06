package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;

import java.util.List;

public interface TaxistasServicio {

    List<TaxistaEntidad> consultar();

    TaxistaEntidad guardar(TaxistaEntidad taxista);

    TaxistaEntidad consultarPorId(String correoUsuario);

    void eliminar(String correoUsuario);

}
