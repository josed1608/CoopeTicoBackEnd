/**
 * Esta es la interfaz para el servicio que maneja la comunicación
 * hacia/desde la Entidad Viaje.
 *
 * @author Joseph Rementería (b55824)
 * @since 06-04-2019
 *
 */
package com.coopetico.coopeticobackend.servicios;

import java.sql.Timestamp;

public interface ViajesServicio {
    /**
     * Guarda una tupla en la base de datos.
     *
     * @author Joseph Rementería (b55824)
     * @since 06-04-2019
     */
    String guardar(
        String placa,
        String correo_cliente,
        Timestamp fecha_inicio,
        Timestamp fecha_fin,
        String costo,
        Integer estrellas,
        String origen_destino,
        String correo_taxista
    );

    /**
     * Este es el método a usar para crear un viaje en el sistema.
     *
     * @author Joseph Rementería (b55824)
     * @since 11-05-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje
     * @param correoUsuario el correo del cliente o la operadora que solicita el viaje
     * @param origen el punto de origen del viaje
     * @param correoTaxista el correo del taxista asignado al viaje
     *
     * @return una string (TODO: find out why? xd)
     */
    String crear(
        String placa,
        Timestamp fechaInicio,
        String correoUsuario,
        String origen,
        String correoTaxista
    );
}
