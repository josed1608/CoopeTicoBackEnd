package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "viaje", schema = "coopetico-dev", catalog = "")
@IdClass(ViajeEntidadPK.class)
public class ViajeEntidad {
    private String pkPlacaTaxi;
    private String pkCorreoCliente;
    private Timestamp pkFechaInicio;
    private Timestamp fechaFin;
    private String costo;
    private Integer estrellas;
    private String origenDestino;
    private String correoTaxi;
    private TaxiEntidad taxiByPkPlacaTaxi;
    private ClienteEntidad clienteByPkCorreoCliente;
    private TaxistaEntidad taxistaByCorreoTaxi;

    @Id
    @Column(name = "pk_placa_taxi", nullable = false, length = 8)
    public String getPkPlacaTaxi() {
        return pkPlacaTaxi;
    }

    public void setPkPlacaTaxi(String pkPlacaTaxi) {
        this.pkPlacaTaxi = pkPlacaTaxi;
    }

    @Id
    @Column(name = "pk_correo_cliente", nullable = false, length = 64)
    public String getPkCorreoCliente() {
        return pkCorreoCliente;
    }

    public void setPkCorreoCliente(String pkCorreoCliente) {
        this.pkCorreoCliente = pkCorreoCliente;
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

    @Basic
    @Column(name = "costo", nullable = false, length = 8)
    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    @Basic
    @Column(name = "estrellas", nullable = true)
    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(Integer estrellas) {
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

    @Basic
    @Column(name = "correo_taxi", nullable = false, length = 8)
    public String getCorreoTaxi() {
        return correoTaxi;
    }

    public void setCorreoTaxi(String correoTaxi) {
        this.correoTaxi = correoTaxi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViajeEntidad that = (ViajeEntidad) o;
        return Objects.equals(pkPlacaTaxi, that.pkPlacaTaxi) &&
                Objects.equals(pkCorreoCliente, that.pkCorreoCliente) &&
                Objects.equals(pkFechaInicio, that.pkFechaInicio) &&
                Objects.equals(fechaFin, that.fechaFin) &&
                Objects.equals(costo, that.costo) &&
                Objects.equals(estrellas, that.estrellas) &&
                Objects.equals(origenDestino, that.origenDestino) &&
                Objects.equals(correoTaxi, that.correoTaxi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkPlacaTaxi, pkCorreoCliente, pkFechaInicio, fechaFin, costo, estrellas, origenDestino, correoTaxi);
    }

    @ManyToOne
    @JoinColumn(name = "pk_placa_taxi", referencedColumnName = "pk_placa", nullable = false)
    public TaxiEntidad getTaxiByPkPlacaTaxi() {
        return taxiByPkPlacaTaxi;
    }

    public void setTaxiByPkPlacaTaxi(TaxiEntidad taxiByPkPlacaTaxi) {
        this.taxiByPkPlacaTaxi = taxiByPkPlacaTaxi;
    }

    @ManyToOne
    @JoinColumn(name = "pk_correo_cliente", referencedColumnName = "pk_correo_usuario", nullable = false)
    public ClienteEntidad getClienteByPkCorreoCliente() {
        return clienteByPkCorreoCliente;
    }

    public void setClienteByPkCorreoCliente(ClienteEntidad clienteByPkCorreoCliente) {
        this.clienteByPkCorreoCliente = clienteByPkCorreoCliente;
    }

    @ManyToOne
    @JoinColumn(name = "correo_taxi", referencedColumnName = "pk_correo_usuario", nullable = false)
    public TaxistaEntidad getTaxistaByCorreoTaxi() {
        return taxistaByCorreoTaxi;
    }

    public void setTaxistaByCorreoTaxi(TaxistaEntidad taxistaByCorreoTaxi) {
        this.taxistaByCorreoTaxi = taxistaByCorreoTaxi;
    }
}
