package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public interface TaxistasServicio {

    @CrossOrigin(origins = "http://localhost:4200")
    List<TaxistaEntidad> consultar();

    TaxistaEntidad guardar(TaxistaEntidad taxista);

    TaxistaEntidad consultarPorId(String correoUsuario);

    void eliminar(String correoUsuario);

}
