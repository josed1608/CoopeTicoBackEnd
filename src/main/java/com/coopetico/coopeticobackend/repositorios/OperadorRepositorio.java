package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.OperadorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadorRepositorio extends JpaRepository<OperadorEntidad, String> {

}
