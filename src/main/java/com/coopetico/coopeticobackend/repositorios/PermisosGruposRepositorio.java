package com.coopetico.coopeticobackend.repositorios;

/**
 Repositorio de la entidad Permisos-Grupo que interactua con la base de datos.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisosGrupoEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisosGruposRepositorio extends JpaRepository<PermisosGrupoEntidad, PermisosGrupoEntidadPK> {

    @Query("select PermisoEntidad from PermisosGrupoEntidad  pg join pg.permisoByPkIdPermisos PermisoEntidad where pg.grupoByPkIdGrupo = :idGrupo")
    List<PermisoEntidad> findPermisosGrupo(GrupoEntidad idGrupo);

}
