package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la entidad Permisos.
 @author      Jefferson Alvarez
 @since       4-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import java.util.List;

public interface PermisosServicio {

    /**
     * Metodo que obtiene los permisos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion
     */
    public List<PermisoEntidad> getPermisos();

}
