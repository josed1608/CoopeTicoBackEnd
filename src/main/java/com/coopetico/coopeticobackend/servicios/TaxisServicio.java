package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import org.springframework.transaction.annotation.Transactional;
import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Interfaz del servicio de taxis
 * @author Jorge Araya González
 */

public interface TaxisServicio {
    @CrossOrigin(origins = "http://localhost:4200")
    List<TaxiEntidad> consultar();
    TaxiEntidad consultarPorId(String placa);
    TaxiEntidad guardar(TaxiEntidad taxi);
    boolean guardarLista(List<TaxiEntidad> taxis);
    void eliminar(String placa);
}