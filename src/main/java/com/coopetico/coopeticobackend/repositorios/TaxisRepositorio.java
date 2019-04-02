package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxisRepositorio extends JpaRepository<TaxiEntidad, String> {

}