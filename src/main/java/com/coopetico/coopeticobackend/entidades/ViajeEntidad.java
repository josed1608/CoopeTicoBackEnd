package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "viaje", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "viajeEntidadPK")
public class ViajeEntidad {
    private ViajeEntidadPK viajeEntidadPK;
    private Timestamp fechaFin;
    private String costo;
    private float estrellas;
    private String origenDestino;
    private TaxiEntidad taxiByPkPlacaTaxi;
    private ClienteEntidad clienteByPkCorreoCliente;
    private TaxistaEntidad taxistaByCorreoTaxi;

    @EmbeddedId
    public ViajeEntidadPK getViajeEntidadPK() {
        return viajeEntidadPK;
    }

    public void setViajeEntidadPK(ViajeEntidadPK viajeEntidadPK) {
        this.viajeEntidadPK = viajeEntidadPK;
    }

    @Basic
    @Column(name = "fecha_fin", nullable = false)
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic
    @Column(name = "costo", nullable = false, length = 8)
    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    @Basic
    @Column(name = "estrellas")
    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    @Basic
    @Column(name = "origen_destino", nullable = false, length = 64)
    public String getOrigenDestino() {
        return origenDestino;
    }

    public void setOrigenDestino(String origenDestino) {
        this.origenDestino = origenDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViajeEntidad that = (ViajeEntidad) o;
        return Objects.equals(viajeEntidadPK, that.viajeEntidadPK) &&
                Objects.equals(fechaFin, that.fechaFin) &&
                Objects.equals(costo, that.costo) &&
                Objects.equals(estrellas, that.estrellas) &&
                Objects.equals(origenDestino, that.origenDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viajeEntidadPK, fechaFin, costo, estrellas, origenDestino);
    }

    @ManyToOne
    @MapsId("pkPlacaTaxi")
    @JoinColumn(name = "pk_placa_taxi", referencedColumnName = "pk_placa", nullable = false)
    public TaxiEntidad getTaxiByPkPlacaTaxi() {
        return taxiByPkPlacaTaxi;
    }

    public void setTaxiByPkPlacaTaxi(TaxiEntidad taxiByPkPlacaTaxi) {
        this.taxiByPkPlacaTaxi = taxiByPkPlacaTaxi;
    }

    @ManyToOne
    @MapsId("pkCorreoCliente")
    @JoinColumn(name = "pk_correo_cliente", referencedColumnName = "pk_correo_usuario", nullable = false)
    public ClienteEntidad getClienteByPkCorreoCliente() {
        return clienteByPkCorreoCliente;
    }

    public void setClienteByPkCorreoCliente(ClienteEntidad clienteByPkCorreoCliente) {
        this.clienteByPkCorreoCliente = clienteByPkCorreoCliente;
    }

    @ManyToOne
    @JoinColumn(name = "correo_taxista", referencedColumnName = "pk_correo_usuario", nullable = false)
    public TaxistaEntidad getTaxistaByCorreoTaxi() {
        return taxistaByCorreoTaxi;
    }

    public void setTaxistaByCorreoTaxi(TaxistaEntidad taxistaByCorreoTaxi) {
        this.taxistaByCorreoTaxi = taxistaByCorreoTaxi;
    }
}
