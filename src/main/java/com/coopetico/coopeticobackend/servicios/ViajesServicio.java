/**
 * Esta es la interfaz para el servicio que maneja la comunicación
 * hacia/desde la Entidad Viaje.
 *
 * @author Joseph Rementería (b55824)
 * @since 06-04-2019
 *
 */
package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;

import java.sql.Timestamp;
import java.util.List;

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
        String fecha_inicio,
        String fecha_fin,
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
    int crear(
        String placa,
        String fechaInicio,
        String correoUsuario,
        String origen,
        String correoTaxista
    );

    /**
     * Este es el método a usar para actualizar la fecha de finalización de un viaje.
     *
     * @author Marco Venegas (B67697)
     * @since 27-05-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje
     * @param fechaFin la fecha en la que finalizó el viaje.
     *
     * @return Int con el estado
     */
    int finalizar(String placa, String fechaInicio, String fechaFin);

    /**
     * Este es el método a usar para actualizar la estrellas de un viaje.
     *
     * @author Marco Venegas (B67697)
     * @since 22-06-2019
     *
     * @param placa la placa del taxi asignado
     * @param fechaInicio la fecha de inicio de un viaje en el formato "yyyy-mm-dd hh:mm:ss"
     * @param estrellas la cantidad de estrellas con las que se calificó el viaje.
     *
     * @return Int con el estado  0 si se actualizó correctamente
     *                           -1 si hubo un problema no manejado.
     *                           -2 si no existe ese viaje en la bd.
     *                           -3 No se puede finalizar un viaje que no ha finalizado.
     *                           -4 No se pueden asignar menos de 1 ni más de 5 estrellas.
     *                           -5 si no se pudo guardar el cambio en la bd.
     */
     int asignarEstrellas(String placa, String fechaInicio, int estrellas);
}
