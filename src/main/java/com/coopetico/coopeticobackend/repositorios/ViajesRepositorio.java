package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajesRepositorio extends JpaRepository<ViajeEntidad, ViajeEntidadPK> {

}
