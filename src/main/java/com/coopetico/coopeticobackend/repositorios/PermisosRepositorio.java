package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisosRepositorio extends JpaRepository<PermisoEntidad, String> {

}
