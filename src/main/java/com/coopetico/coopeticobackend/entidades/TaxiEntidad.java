package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "taxi", schema = "coopetico-dev", catalog = "")
public class TaxiEntidad {
    private String pkPlaca;
    private Boolean datafono;
    private String telefono;
    private Object clase;
    private Object tipo;
    private Timestamp fechaVenTaxista;
    private Timestamp fechaVenMarchamo;
    private Timestamp fechaVenSeguro;
    private Collection<TaxistaEntidad> taxistasByPkPlaca;
    private Collection<TaxistaEntidad> taxistasByPkPlaca_0;
    private Collection<ViajeEntidad> viajesByPkPlaca;

    @Id
    @Column(name = "pk_placa", nullable = false, length = 8)
    public String getPkPlaca() {
        return pkPlaca;
    }

    public void setPkPlaca(String pkPlaca) {
        this.pkPlaca = pkPlaca;
    }

    @Basic
    @Column(name = "datafono", nullable = true)
    public Boolean getDatafono() {
        return datafono;
    }

    public void setDatafono(Boolean datafono) {
        this.datafono = datafono;
    }

    @Basic
    @Column(name = "telefono", nullable = false, length = 8)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "clase", nullable = false)
    public Object getClase() {
        return clase;
    }

    public void setClase(Object clase) {
        this.clase = clase;
    }

    @Basic
    @Column(name = "tipo", nullable = false)
    public Object getTipo() {
        return tipo;
    }

    public void setTipo(Object tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "fecha_ven_taxista", nullable = false)
    public Timestamp getFechaVenTaxista() {
        return fechaVenTaxista;
    }

    public void setFechaVenTaxista(Timestamp fechaVenTaxista) {
        this.fechaVenTaxista = fechaVenTaxista;
    }

    @Basic
    @Column(name = "fecha_ven_marchamo", nullable = false)
    public Timestamp getFechaVenMarchamo() {
        return fechaVenMarchamo;
    }

    public void setFechaVenMarchamo(Timestamp fechaVenMarchamo) {
        this.fechaVenMarchamo = fechaVenMarchamo;
    }

    @Basic
    @Column(name = "fecha_ven_seguro", nullable = false)
    public Timestamp getFechaVenSeguro() {
        return fechaVenSeguro;
    }

    public void setFechaVenSeguro(Timestamp fechaVenSeguro) {
        this.fechaVenSeguro = fechaVenSeguro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxiEntidad that = (TaxiEntidad) o;
        return Objects.equals(pkPlaca, that.pkPlaca) &&
                Objects.equals(datafono, that.datafono) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(clase, that.clase) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(fechaVenTaxista, that.fechaVenTaxista) &&
                Objects.equals(fechaVenMarchamo, that.fechaVenMarchamo) &&
                Objects.equals(fechaVenSeguro, that.fechaVenSeguro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkPlaca, datafono, telefono, clase, tipo, fechaVenTaxista, fechaVenMarchamo, fechaVenSeguro);
    }

    @OneToMany(mappedBy = "taxiByPlacaTaxiManeja")
    public Collection<TaxistaEntidad> getTaxistasByPkPlaca() {
        return taxistasByPkPlaca;
    }

    public void setTaxistasByPkPlaca(Collection<TaxistaEntidad> taxistasByPkPlaca) {
        this.taxistasByPkPlaca = taxistasByPkPlaca;
    }

    @OneToMany(mappedBy = "taxiByPlacaTaxiDueno")
    public Collection<TaxistaEntidad> getTaxistasByPkPlaca_0() {
        return taxistasByPkPlaca_0;
    }

    public void setTaxistasByPkPlaca_0(Collection<TaxistaEntidad> taxistasByPkPlaca_0) {
        this.taxistasByPkPlaca_0 = taxistasByPkPlaca_0;
    }

    @OneToMany(mappedBy = "taxiByPkPlacaTaxi")
    public Collection<ViajeEntidad> getViajesByPkPlaca() {
        return viajesByPkPlaca;
    }

    public void setViajesByPkPlaca(Collection<ViajeEntidad> viajesByPkPlaca) {
        this.viajesByPkPlaca = viajesByPkPlaca;
    }
}
