package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.OperadorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoopeticosRepositorio extends JpaRepository<OperadorEntidad, String> {

}
