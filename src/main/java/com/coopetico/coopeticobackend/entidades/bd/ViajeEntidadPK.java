package com.coopetico.coopeticobackend.entidades.bd;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class ViajeEntidadPK implements Serializable {
    private String pkPlacaTaxi;
    private String pkFechaInicio;

    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    @Column(name = "pk_fecha_inicio", nullable = false)
    public String getPkFechaInicio() {
        return pkFechaInicio;
    }

    public void setPkFechaInicio(String pkFechaInicio) {
        this.pkFechaInicio = pkFechaInicio;
    }

    public ViajeEntidadPK(String pkPlacaTaxi, String pkFechaInicio) {
        this.pkPlacaTaxi = pkPlacaTaxi;
        this.pkFechaInicio = pkFechaInicio;
    }

    public ViajeEntidadPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViajeEntidadPK that = (ViajeEntidadPK) o;
        return Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi) &&
                Objects.equals(pkFechaInicio, that.pkFechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkPlacaTaxi, pkFechaInicio);
    }
}
