package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conduce", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "conduceEntidadPK")
public class ConduceEntidad {
    private ConduceEntidadPK conduceEntidadPK;
    private TaxistaEntidad taxistaConduce;
    private TaxiEntidad taxiConducido;

    public ConduceEntidad(ConduceEntidadPK conduceEntidadPK, TaxistaEntidad taxistaConduce, TaxiEntidad taxiConducido) {
        this.conduceEntidadPK = conduceEntidadPK;
        this.taxistaConduce = taxistaConduce;
        this.taxiConducido = taxiConducido;
    }

    public ConduceEntidad() {
    }

    @EmbeddedId
    public ConduceEntidadPK getConduceEntidadPK() {
        return conduceEntidadPK;
    }

    public void setConduceEntidadPK(ConduceEntidadPK conduceEntidadPK) {
        this.conduceEntidadPK = conduceEntidadPK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConduceEntidad that = (ConduceEntidad) o;
        return Objects.equals(conduceEntidadPK, that.conduceEntidadPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conduceEntidadPK);
    }

    @ManyToOne
    @MapsId("pkCorreoTaxista")
    @JoinColumn(name = "pk_correo_taxista", referencedColumnName = "pk_correo_usuario", nullable = false)
    public TaxistaEntidad getTaxistaConduce() {
        return taxistaConduce;
    }

    public void setTaxistaConduce(TaxistaEntidad taxistaConduce) {
        this.taxistaConduce = taxistaConduce;
    }

    @ManyToOne
    @MapsId("pkPlacaTaxi")
    @JoinColumn(name = "pk_placa_taxi", referencedColumnName = "pk_placa", nullable = false)
    public TaxiEntidad getTaxiConducido() {
        return taxiConducido;
    }

    public void setTaxiConducido(TaxiEntidad taxiConducido) {
        this.taxiConducido = taxiConducido;
    }
}
