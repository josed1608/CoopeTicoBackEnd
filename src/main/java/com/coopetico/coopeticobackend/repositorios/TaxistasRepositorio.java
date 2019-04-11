package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
public interface TaxistasRepositorio extends JpaRepository<TaxistaEntidad, String> {

}
