package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 Interfaz del servicio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface TaxistasServicio {

    /**
     * Funcion que retorna los taxistas del sistema.
     * @return Lista de taxistas del sistema.
     */
    List<TaxistaEntidadTemporal> consultar();

    /**
     * Funcion que guarda la informacion del taxista que entra por parametro.
     * @param taxista Taxista que se quiere guardar.
     * @param pkCorreoUsuario Antiguo correo del taxista que se quiere guardar.
     * @return Taxista guardado en el sistema.
     */
    TaxistaEntidadTemporal guardar(TaxistaEntidadTemporal taxista, String pkCorreoUsuario);

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere consultar
     * @return Taxista solicitado.
     */
    TaxistaEntidadTemporal consultarPorId(String correoUsuario);

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere eliminar
     */
    void eliminar(String correoUsuario);

}