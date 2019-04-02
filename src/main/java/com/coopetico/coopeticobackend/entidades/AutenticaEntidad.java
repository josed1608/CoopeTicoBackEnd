package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "autentica", schema = "coopetico-dev", catalog = "")
@IdClass(AutenticaEntidadPK.class)
public class AutenticaEntidad {
    private String pkCorreoTaxista;
    private String pkPlacaTaxi;
    private Timestamp pkFechaInicio;
    private Timestamp fechaFin;
    private TaxistaEntidad taxistaByPkCorreoTaxista;
    private TaxiEntidad taxiByPkPlacaTaxi;

    @Id
    @Column(name = "pk_correo_taxista", nullable = false, length = 64)
    public String getPkCorreoTaxista() {
        return pkCorreoTaxista;
    }

    public void setPkCorreoTaxista(String pkCorreoTaxista) {
        this.pkCorreoTaxista = pkCorreoTaxista;
    }

    @Id
    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    @Id
    @Column(name = "pk_fecha_inicio", nullable = false)
    public Timestamp getPkFechaInicio() {
        return pkFechaInicio;
    }

    public void setPkFechaInicio(Timestamp pkFechaInicio) {
        this.pkFechaInicio = pkFechaInicio;
    }

    @Basic
    @Column(name = "fecha_fin", nullable = false)
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutenticaEntidad that = (AutenticaEntidad) o;
        return Objects.equals(pkCorreoTaxista, that.pkCorreoTaxista) &&
                Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi) &&
                Objects.equals(pkFechaInicio, that.pkFechaInicio) &&
                Objects.equals(fechaFin, that.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreoTaxista, pkPlacaTaxi, pkFechaInicio, fechaFin);
    }

    @ManyToOne
    @JoinColumn(name = "pk_correo_taxista", referencedColumnName = "pk_correo_usuario", nullable = false)
    public TaxistaEntidad getTaxistaByPkCorreoTaxista() {
        return taxistaByPkCorreoTaxista;
    }

    public void setTaxistaByPkCorreoTaxista(TaxistaEntidad taxistaByPkCorreoTaxista) {
        this.taxistaByPkCorreoTaxista = taxistaByPkCorreoTaxista;
    }

    @ManyToOne
    @JoinColumn(name = "pk_placa_taxi", referencedColumnName = "pk_placa", nullable = false)
    public TaxiEntidad getTaxiByPkPlacaTaxi() {
        return taxiByPkPlacaTaxi;
    }

    public void setTaxiByPkPlacaTaxi(TaxiEntidad taxiByPkPlacaTaxi) {
        this.taxiByPkPlacaTaxi = taxiByPkPlacaTaxi;
    }
}
