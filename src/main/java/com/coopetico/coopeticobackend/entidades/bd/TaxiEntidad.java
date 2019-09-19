package com.coopetico.coopeticobackend.entidades.bd;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "taxi", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkPlaca")
public class TaxiEntidad {
    private String pkPlaca;
    private Boolean datafono;
    private String telefono;
    private String clase;
    private String tipo;
    private String foto;
    private Boolean valido;
    private Timestamp fechaVenRtv;
    private Timestamp fechaVenMarchamo;
    private Timestamp fechaVenSeguro;
    private boolean estado;
    private String justificacion;
    private TaxistaEntidad taxistaActual;
    private TaxistaEntidad duennoTaxi;
    @JsonIgnore
    private Collection<ViajeEntidad> viajesByPkPlaca;
    private Collection<ConduceEntidad> taxistasQueMeConducen;

    public TaxiEntidad(String pkPlaca, Boolean datafono, String telefono, String clase, String tipo,
                       Timestamp fechaVenRtv, Timestamp fechaVenMarchamo, Timestamp fechaVenSeguro,
                       TaxistaEntidad duennoTaxi, Collection<ViajeEntidad> viajesByPkPlaca, String foto, Boolean valido,
                       boolean estado, String justificacion, TaxistaEntidad taxistaActual, Collection<ConduceEntidad> taxistasQueMeConducen) {
        this.pkPlaca = pkPlaca;
        this.datafono = datafono;
        this.telefono = telefono;
        this.clase = clase;
        this.tipo = tipo;
        this.duennoTaxi = duennoTaxi;
        this.foto = foto;
        this.valido = valido;
        this.fechaVenRtv = fechaVenRtv;
        this.fechaVenMarchamo = fechaVenMarchamo;
        this.fechaVenSeguro = fechaVenSeguro;
        this.estado = estado;
        this.justificacion = justificacion;
        this.taxistaActual = taxistaActual;
        this.viajesByPkPlaca = viajesByPkPlaca;
        this.taxistasQueMeConducen = taxistasQueMeConducen;
    }

    public TaxiEntidad(String pkPlaca, Boolean datafono, String telefono, String clase, String tipo, Date fechaVenRtv, Date fechaVenMarchamo, Date fechaVenSeguro, boolean estado, String justificacion, Boolean valido, String foto, TaxistaEntidad duenno) {
        this.pkPlaca = pkPlaca;
        this.datafono = datafono;
        this.telefono = telefono;
        this.clase = clase;
        this.tipo = tipo;
        this.foto = foto;
        this.valido = valido;
        this.fechaVenRtv = new Timestamp(fechaVenRtv.getTime());
        this.fechaVenMarchamo = new Timestamp(fechaVenMarchamo.getTime());
        this.fechaVenSeguro = new Timestamp(fechaVenSeguro.getTime());
        this.estado = estado;
        this.justificacion = justificacion;
        this.duennoTaxi = duenno;
    }

    public TaxiEntidad(){}

    @Id
    @Column(name = "pk_placa", nullable = false, length = 8)
    public String getPkPlaca() {
        return pkPlaca;
    }

    public void setPkPlaca(String pkPlaca) {
        this.pkPlaca = pkPlaca;
    }

    @Basic
    @Column(name = "datafono")
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
    @Column(name = "clase", nullable = false, columnDefinition = "ENUM('A','B','NA')")
    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    @Basic
    @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('microbus','wagon','normal')")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "foto", nullable = false)
    public String getFoto() { return foto; }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Basic
    @Column(name = "valido", nullable = false)
    public Boolean getValido() { return valido; }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    @Basic
    @Column(name = "estado", nullable = false)
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Basic
    @Column(name = "justificacion", nullable = false)
    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "correo_taxista")
    public TaxistaEntidad getDuennoTaxi() {
        return duennoTaxi;
    }

    public void setDuennoTaxi(TaxistaEntidad duennoTaxi) {
        this.duennoTaxi = duennoTaxi;
    }

    @Basic
    @Column(name = "fecha_ven_rtv", nullable = false)
    public Timestamp getFechaVenRtv() {
        return fechaVenRtv;
    }

    public void setFechaVenRtv(Timestamp fechaVenTaxista) {
        this.fechaVenRtv = fechaVenTaxista;
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
                Objects.equals(fechaVenRtv, that.fechaVenRtv) &&
                Objects.equals(fechaVenMarchamo, that.fechaVenMarchamo) &&
                Objects.equals(fechaVenSeguro, that.fechaVenSeguro) &&
                Objects.equals(estado, that.estado) &&
                Objects.equals(justificacion, that.justificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkPlaca, datafono, telefono, clase, tipo, foto, fechaVenRtv, fechaVenMarchamo, fechaVenSeguro, estado, justificacion);
    }

    @OneToMany(mappedBy = "taxiByPkPlacaTaxi")
    public Collection<ViajeEntidad> getViajesByPkPlaca() {
        return viajesByPkPlaca;
    }

    public void setViajesByPkPlaca(Collection<ViajeEntidad> viajesByPkPlaca) {
        this.viajesByPkPlaca = viajesByPkPlaca;
    }

    @OneToMany(mappedBy = "taxiConducido")
    public Collection<ConduceEntidad> getTaxistasQueMeConducen() {
        return taxistasQueMeConducen;
    }

    public void setTaxistasQueMeConducen(Collection<ConduceEntidad> taxistasQueMeConducen) {
        this.taxistasQueMeConducen = taxistasQueMeConducen;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "taxiActual")
    public TaxistaEntidad getTaxistaActual() {
        return taxistaActual;
    }

    public void setTaxistaActual(TaxistaEntidad taxistaActual) {
        this.taxistaActual = taxistaActual;
    }
}
