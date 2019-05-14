package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuariosRepositorio extends JpaRepository<UsuarioEntidad, String> {

    /**
     * Metodo que permite encontrar los usuarios de un grupo
     * @param grupo grupo que se quiere buscar
     * @return Usuarios del grrupo
     */
    List<UsuarioEntidad> findByGrupoByIdGrupo(GrupoEntidad grupo);


}
