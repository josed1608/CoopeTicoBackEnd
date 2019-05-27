package com.coopetico.coopeticobackend.entidades.bd;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConduceEntidadPK implements Serializable {
    private String pkCorreoTaxista;
    private String pkPlacaTaxi;

    @Column(name = "pk_correo_taxista", nullable = false, length = 64)
    public String getPkCorreoTaxista() {
        return pkCorreoTaxista;
    }

    public void setPkCorreoTaxista(String pkCorreoTaxista) {
        this.pkCorreoTaxista = pkCorreoTaxista;
    }

    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    public ConduceEntidadPK(String pkCorreoTaxista, String pkPlacaTaxi) {
        this.pkCorreoTaxista = pkCorreoTaxista;
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    public ConduceEntidadPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConduceEntidadPK that = (ConduceEntidadPK) o;
        return Objects.equals(pkCorreoTaxista, that.pkCorreoTaxista) &&
                Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreoTaxista, pkPlacaTaxi);
    }
}
