package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ViajesRepositorio extends JpaRepository<ViajeEntidad, ViajeEntidadPK> {

    @Query(
            value = "SELECT * FROM viaje v WHERE v.pk_placa_taxi = ?1 AND v.pk_fecha_inicio = ?2",
            nativeQuery = true
    )
    ViajeEntidad encontrarViaje(
            String placa,
            String fechaInicio
    );

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM viaje v WHERE v.pk_placa_taxi = ?1 AND v.pk_fecha_inicio = ?2",
            nativeQuery = true
    )
    void eliminarViaje(
            String placa,
            String fechaInicio
    );
}
