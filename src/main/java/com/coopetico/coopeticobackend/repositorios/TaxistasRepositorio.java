package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 Repositorio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface TaxistasRepositorio extends JpaRepository<TaxistaEntidad, String> {
}
