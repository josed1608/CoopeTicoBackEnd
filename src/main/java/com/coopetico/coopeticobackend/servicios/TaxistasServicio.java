package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * Encuentra un usuario por correo     *
     * @param correo correo del usuario que se va a buscar
     * @return retorna el usuario en un Optional para poder aplicar lógica en caso de que no lo encuentre
     */
    Optional<TaxistaEntidad> taxistaPorCorreo(String correo);

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere eliminar
     */
    void eliminar(String correoUsuario);

    /**
     * Devuelve el estado del taxista
     * @param correo Correo del taxista
     * @return Mapa con el estado del taxista, y en caso de estar bloqueado, la justificacion
     * @author Kevin Jiménez
     */
    Map<String, Object> obtenerEstado(String correo);

    /**
     * Trae de la base la entidad taxista identificada al corro dado
     *
     * @author Joseph Rementeríá (b55824)
     * @since 11-05-2019
     *
     * @param correo el correo del usuario
     * @return la entidad si existe, null de otra manera
     */
    TaxistaEntidad consultarTaxistaPorId(String correo);
    /**
     * Trae los datos del taxista asociados al correo parametrisado.
     * Se despiega en flutter cuando el usuario ve los datos del cofer asignado.
     *
     * @author Joseph Rementería (b55824)
     * @since 15-05-2019
     *
     * @param correoTaxista correo del taxista asignado
     * @return datos a mostrar en flutter.
     */
    DatosTaxistaAsigadoEntidad obtenerDatosTaxistaAsignado(String correoTaxista);

    /**
     * Método para guardar una lista de taxistas en la base de datos.
     * @param taxistas Lista Entidad taxistas que se quiere guardar
     * @return true si es correcto o false si falla
     */
    boolean guardarLista(List<TaxistaEntidadTemporal> taxistas);
}
