package com.coopetico.coopeticobackend.entidades;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ViajeEntidadPK implements Serializable {
    private String pkPlacaTaxi;
    private String pkCorreoCliente;
    private Timestamp pkFechaInicio;

    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    @Id
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    @Column(name = "pk_correo_cliente", nullable = false, length = 64)
    @Id
    public String getPkCorreoCliente() {
        return pkCorreoCliente;
    }

    public void setPkCorreoCliente(String pkCorreoCliente) {
        this.pkCorreoCliente = pkCorreoCliente;
    }

    @Column(name = "pk_fecha_inicio", nullable = false)
    @Id
    public Timestamp getPkFechaInicio() {
        return pkFechaInicio;
    }

    public void setPkFechaInicio(Timestamp pkFechaInicio) {
        this.pkFechaInicio = pkFechaInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViajeEntidadPK that = (ViajeEntidadPK) o;
        return Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi) &&
                Objects.equals(pkCorreoCliente, that.pkCorreoCliente) &&
                Objects.equals(pkFechaInicio, that.pkFechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkPlacaTaxi, pkCorreoCliente, pkFechaInicio);
    }
}
