package com.coopetico.coopeticobackend.repositorios;
 /**
 Repositorio de la entidad Permisos que interactua con la base de datos.
 @author      Jefferson Alvarez
 @since       4-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisosRepositorio extends JpaRepository<PermisoEntidad, Integer> {

    /**
     * Metodo que obtiene el id y la descripcion de los permisos
     * @return Lista de entidades de Permisos con sus atributos pkId y descripcion
     */
    @Query("select new PermisoEntidad (p.pkId as pkId, p.descripcion as descripcion) from PermisoEntidad p")
    List<PermisoEntidad> getPermisoIDyDescripcion();
}
