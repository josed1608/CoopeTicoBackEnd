package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajesRepositorio extends JpaRepository<ViajeEntidad, ViajeEntidadPK> {

}
