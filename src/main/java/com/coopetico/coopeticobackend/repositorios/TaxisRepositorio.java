package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Repositorio de la entidad taxi.
 * @author Jorge Araya González
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface TaxisRepositorio extends JpaRepository<TaxiEntidad, String> {

}