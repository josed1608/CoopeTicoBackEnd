package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.CoopeticoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoopeticosRepositorio extends JpaRepository<CoopeticoEntidad, String> {

}
