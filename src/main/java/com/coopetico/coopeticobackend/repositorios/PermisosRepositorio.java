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

    @Query("select new map(p.descripcion as descripcion, p.pkId as pkId) from PermisoEntidad p")
    List<PermisoEntidad> getPermisoIDyDescripcion();
}
