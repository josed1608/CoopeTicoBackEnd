package com.coopetico.coopeticobackend.servicios;

/**
 Interfaz del servicio de la entidad Permisos-Grupo que provee los metodos a utilizar.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;

import java.util.List;

public interface PermisoGrupoServicio {


     /**
     * Metodo que obtiene los permisos de los grupos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion del permiso y el ID del grupo
     */
    public List<PermisosGrupoEntidad> getPermisosGrupo(GrupoEntidad idGrupo);

     /**
     * Metodo que guarda una nueva llave permisos-grupo en la base de datos
     * @return true si las llaves se han guardado correctamente
     * @param pGLista Lista que guarda pares pkIdPermisos, pkIdGrupo a ser guardados en la base de datos
     */
    public boolean guardarPermisosGrupo(List<PermisosGrupoEntidadPK> pGLista);

     /**
     * Metodo que elimina una llave permisos-grupo en la base de datos
      * @return true si las llaves se han eliminado correctamente
      * @param pGLista Lista que guarda pares pkIdPermisos, pkIdGrupo a ser guardados en la base de datos
     */
    public boolean eliminarPermisosGrupo(List<PermisosGrupoEntidadPK> pGLista);
}
