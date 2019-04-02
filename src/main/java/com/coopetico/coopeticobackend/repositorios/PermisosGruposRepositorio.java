package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisosGruposRepositorio extends JpaRepository<PermisosGrupoEntidad, PermisosGrupoEntidadPK> {

}
