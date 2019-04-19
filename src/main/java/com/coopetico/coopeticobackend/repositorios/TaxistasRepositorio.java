package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxistasRepositorio extends JpaRepository<TaxistaEntidad, String> {

}
