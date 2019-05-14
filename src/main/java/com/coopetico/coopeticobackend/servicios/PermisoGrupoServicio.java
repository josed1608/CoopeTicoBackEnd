package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la entidad Permisos-Grupo que provee los metodos a utilizar.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisosGrupoEntidadPK;

import java.util.List;

public interface PermisoGrupoServicio {


     /**
     * Metodo que obtiene los permisos del grupo
     * @return Lista de permisos con el ID y la Descripcion del permiso, del grupo
     */
    public List<PermisoEntidad> getPermisosGrupo(GrupoEntidad idGrupo);

     /**
     * Metodo que guarda una nueva llave permisos-grupo en la base de datos
     * @return true si las llaves se han guardado correctamente
     * @param pG Lista que guarda el par pkIdPermisos, pkIdGrupo a ser guardado en la base de datos
     */
    public boolean guardarPermisosGrupo(PermisosGrupoEntidad pG);

     /**
     * Metodo que elimina una llave permisos-grupo en la base de datos
      * @return true si las llaves se han eliminado correctamente
      * @param pG Permiso-grupo que guarda el par pkIdPermisos, pkIdGrupo a ser eliminado en la base de datos
     */
    public boolean eliminarPermisosGrupo(PermisosGrupoEntidad pG);

    /**
     * Metodo que obtiene un objeto de la entidad Permiso-Grupo
     * @param permisoGrupoPK Llave primaria del objeto de interes
     * @return Objeto de la entidad Permiso-Grupo
     */
    public PermisosGrupoEntidad getPermisoGrupoPorPK (PermisosGrupoEntidadPK permisoGrupoPK);
}
