package com.coopetico.coopeticobackend.servicios;

 /**
 Interfaz del servicio de la entidad Grupo.
 @author      Jefferson Alvarez
 @since       13-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import java.util.List;

public interface GrupoServicio {

    /**
     * Metodo que obtiene los grupos existentes del sistema
     * @return Lista de grupos con el ID
     */
    public List<GrupoEntidad> getGrupos();

    /**
     * Metodo que obtiene un objeto de la entidad Grupo
     * @param grupoPK Llave primaria del objeto de interes
     * @return Objeto de la entidad Grupo
     */
    public GrupoEntidad getGrupoPorPK(String grupoPK);

}
