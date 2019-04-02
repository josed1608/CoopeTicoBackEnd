package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.ViajeEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajesRepositorio extends JpaRepository<ViajeEntidad, String> {

}
