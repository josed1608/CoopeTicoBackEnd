package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 Repositorio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version:    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface TaxistasRepositorio extends JpaRepository<TaxistaEntidad, String> {

    /**
     * Funcion que retorna los taxistas del sistema.
     * @return Lista de taxistas del sistema.
     */
    @Query("select new map(t.pkCorreoUsuario as pkCorreoUsuario, u.nombre as nombre, u.apellidos as apellidos, " +
            "u.telefono as telefono, t.estado as estado) from TaxistaEntidad t join t.usuarioByPkCorreoUsuario u")
    List<TaxistaEntidad> consultar();
}
