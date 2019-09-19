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
    List<TaxiEntidad> consultar();
    TaxiEntidad consultarPorId(String placa);
    TaxiEntidad guardar(TaxiEntidad taxi);

    /**
     * Método para guardar una lista de taxis en la base de datos.
     * @param taxis Lista Entidad taxi que se quiere guardar
     * @return true si es correcto o false si falla
     */
    boolean guardarLista(List<TaxiEntidad> taxis);
    void eliminar(String placa);
}