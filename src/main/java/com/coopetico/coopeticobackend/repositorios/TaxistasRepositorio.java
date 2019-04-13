package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public interface TaxistasRepositorio extends JpaRepository<TaxistaEntidad, String> {

    @Query("select new map(t.pkCorreoUsuario as pkCorreoUsuario, u.nombre as nombre, u.apellidos as apellidos, " +
            "u.telefono as telefono, t.estado as estado) from TaxistaEntidad t join t.usuarioByPkCorreoUsuario u")
    List<TaxistaEntidad> consultar();
}
