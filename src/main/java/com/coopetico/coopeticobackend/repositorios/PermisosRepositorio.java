package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisosRepositorio extends JpaRepository<PermisoEntidad, String> {

        @Override
        @Query("select p.pkId, p.descripcion from PermisoEntidad p ")
        List<PermisoEntidad> findAll();
}
