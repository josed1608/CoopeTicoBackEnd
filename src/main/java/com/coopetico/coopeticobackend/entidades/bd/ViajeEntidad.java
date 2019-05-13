package com.coopetico.coopeticobackend.entidades.bd;

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
    private String origen;
    private String destino;
    private String agendaTelefono;
    private String agendaNombre;

    private TaxiEntidad taxiByPkPlacaTaxi;
    private ClienteEntidad clienteByPkCorreoCliente;
    private TaxistaEntidad taxistaByCorreoTaxi;
    private OperadorEntidad agendaOperador;

    public ViajeEntidad(ViajeEntidadPK viajeEntidadPK, Timestamp fechaFin, String costo, float estrellas, String origen, String destino, String agendaTelefono, String agendaNombre, TaxiEntidad taxiByPkPlacaTaxi, ClienteEntidad clienteByPkCorreoCliente, TaxistaEntidad taxistaByCorreoTaxi, OperadorEntidad agendaOperador) {
        this.viajeEntidadPK = viajeEntidadPK;
        this.fechaFin = fechaFin;
        this.costo = costo;
        this.estrellas = estrellas;
        this.origen = origen;
        this.destino = destino;
        this.agendaTelefono = agendaTelefono;
        this.agendaNombre = agendaNombre;
        this.taxiByPkPlacaTaxi = taxiByPkPlacaTaxi;
        this.clienteByPkCorreoCliente = clienteByPkCorreoCliente;
        this.taxistaByCorreoTaxi = taxistaByCorreoTaxi;
        this.agendaOperador = agendaOperador;
    }

    public ViajeEntidad() {
    }

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
    @Column(name = "origen", nullable = false, length = 64)
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Basic
    @Column(name = "destino", nullable = false, length = 64)
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Basic
    @Column(name = "agenda_telefono")
    public String getAgendaTelefono() {
        return agendaTelefono;
    }

    public void setAgendaTelefono(String agendaTelefono) {
        this.agendaTelefono = agendaTelefono;
    }

    @Basic
    @Column(name = "agenda_nombre")
    public String getAgendaNombre() {
        return agendaNombre;
    }

    public void setAgendaNombre(String agendaNombre) {
        this.agendaNombre = agendaNombre;
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
                Objects.equals(origen, that.origen) &&
                Objects.equals(destino, that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viajeEntidadPK, fechaFin, costo, estrellas, origen, destino);
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
    @JoinColumn(name = "fk_correo_cliente", referencedColumnName = "pk_correo_usuario", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "fk_agenda_correo_operador", referencedColumnName = "pk_correo_usuario", nullable = false)
    public OperadorEntidad getAgendaOperador() {
        return agendaOperador;
    }

    public void setAgendaOperador(OperadorEntidad agendaOperador) {
        this.agendaOperador = agendaOperador;
    }
}
