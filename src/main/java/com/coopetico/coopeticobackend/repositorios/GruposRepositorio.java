package com.coopetico.coopeticobackend.repositorios;

/**
 Repositorio de la entidad Grupo que interactua con la base de datos.
 @author      Jefferson Alvarez
 @since       13-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GruposRepositorio extends JpaRepository<GrupoEntidad, String> {

    /**
     * Metodo que obtiene el id de los grupos
     * @return Lista de entidades de Grupos con su atributo pkId
     */
    @Query("select new map(g.pkId as pkId) from GrupoEntidad g")
    List<GrupoEntidad> getIDGrupos();

}
