package com.coopetico.coopeticobackend.repositorios;

/**
 Repositorio de la entidad Permisos-Grupo que interactua con la base de datos.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisosGruposRepositorio extends JpaRepository<PermisosGrupoEntidad, PermisosGrupoEntidadPK> {

    @Query("select new map (pg.permisoByPkIdPermisos as permisoByPkIdPermisos) from PermisosGrupoEntidad  pg where pg.grupoByPkIdGrupo = :idGrupo")
    List<PermisosGrupoEntidad> findPermisosGrupo(GrupoEntidad idGrupo);

}
