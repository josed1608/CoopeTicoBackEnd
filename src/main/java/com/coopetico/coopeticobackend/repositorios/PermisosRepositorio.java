package com.coopetico.coopeticobackend.repositorios;
//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.1
//Repositorio de la entidad Permisos.

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisosRepositorio extends JpaRepository<PermisoEntidad, String> {

        @Override
        @Query("select p.pkId, p.descripcion from PermisoEntidad p ")
        List<PermisoEntidad> findAll();
}
