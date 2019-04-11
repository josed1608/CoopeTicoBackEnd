package com.coopetico.coopeticobackend.repositorios;
 /**
 Repositorio de la entidad Permisos que interactua con la base de datos.
 @author      Jefferson Alvarez
 @since       4-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisosRepositorio extends JpaRepository<PermisoEntidad, Integer> {
        
}
