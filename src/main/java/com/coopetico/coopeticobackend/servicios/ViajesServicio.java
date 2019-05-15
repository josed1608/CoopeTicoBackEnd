/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * <p>
 * Fecha: 06/04/2019.
 * <p>
 * Esta es la interfaz para el servicio que maneja la comunicación
 * hacia/desde la Entidad Viaje.
 */
package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;

import java.sql.Timestamp;
import java.util.List;

public interface ViajesServicio {
    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Guarda una tupla en la base de datos.
     *
     */
    String guardar(
        String placa,
        String correo_cliente,
        Timestamp fecha_inicio,
        Timestamp fecha_fin,
        String costo,
        Integer estrellas,
        String origen,
        String destino,
        String correo_taxista
    );

    /**
     * Permite obtener todos los viajes
     * @return Retorna una lista de viajes
     */
    List<ViajeEntidad> consultarViajes();

}
