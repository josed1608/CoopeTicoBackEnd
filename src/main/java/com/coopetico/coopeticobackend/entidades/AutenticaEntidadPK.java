package com.coopetico.coopeticobackend.entidades;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class AutenticaEntidadPK implements Serializable {
    private String pkCorreoTaxista;
    private String pkPlacaTaxi;
    private Timestamp pkFechaInicio;

    @Column(name = "pk_correo_taxista", nullable = false, length = 64)
    @Id
    public String getPkCorreoTaxista() {
        return pkCorreoTaxista;
    }

    public void setPkCorreoTaxista(String pkCorreoTaxista) {
        this.pkCorreoTaxista = pkCorreoTaxista;
    }

    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    @Id
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
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
        AutenticaEntidadPK that = (AutenticaEntidadPK) o;
        return Objects.equals(pkCorreoTaxista, that.pkCorreoTaxista) &&
                Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi) &&
                Objects.equals(pkFechaInicio, that.pkFechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreoTaxista, pkPlacaTaxi, pkFechaInicio);
    }
}
