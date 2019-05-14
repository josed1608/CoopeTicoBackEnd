package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.ConduceEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConduceRepositorio extends JpaRepository<ConduceEntidad, String> {

}
