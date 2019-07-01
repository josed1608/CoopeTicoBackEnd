package com.coopetico.coopeticobackend.entidades.bd;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "taxista", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkCorreoUsuario")
public class TaxistaEntidad {
    private String pkCorreoUsuario;
    private String faltas;
    private boolean estado;
    private boolean hojaDelincuencia;
    private float estrellas;
    private String justificacion;
    private Timestamp vence_licencia;
    private TaxiEntidad taxiActual;
    private Collection<TaxiEntidad> taxiPropiedad;
    private UsuarioEntidad usuarioByPkCorreoUsuario;
    private Collection<ViajeEntidad> viajesByPkCorreoUsuario;
    private Collection<ConduceEntidad> taxisConducidos;

    public TaxistaEntidad(String pkCorreoUsuario, String faltas, boolean estado, boolean hojaDelincuencia, float estrellas, String justificacion, Timestamp vence_licencia, TaxiEntidad taxiActual, Collection<TaxiEntidad> taxiPropiedad, UsuarioEntidad usuarioByPkCorreoUsuario, Collection<ViajeEntidad> viajesByPkCorreoUsuario, Collection<ConduceEntidad> taxisConducidos) {
        this.pkCorreoUsuario = pkCorreoUsuario;
        this.faltas = faltas;
        this.estado = estado;
        this.hojaDelincuencia = hojaDelincuencia;
        this.estrellas = estrellas;
        this.justificacion = justificacion;
        this.vence_licencia = vence_licencia;
        this.taxiActual = taxiActual;
        this.taxiPropiedad = taxiPropiedad;
        this.usuarioByPkCorreoUsuario = usuarioByPkCorreoUsuario;
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
        this.taxisConducidos = taxisConducidos;
    }

    public TaxistaEntidad() {
    }

    @Id
    @Column(name = "pk_correo_usuario", nullable = false, length = 64)
    public String getPkCorreoUsuario() {
        return pkCorreoUsuario;
    }

    public void setPkCorreoUsuario(String pkCorreoUsuario) {
        this.pkCorreoUsuario = pkCorreoUsuario;
    }

    @Basic
    @Column(name = "faltas", nullable = false, columnDefinition = "ENUM('1','2','3','0')")
    public String getFaltas() {
        return faltas;
    }

    public void setFaltas(String faltas) {
        this.faltas = faltas;
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
    @Column(name = "hoja_delincuencia", nullable = false)
    public boolean isHojaDelincuencia() {
        return hojaDelincuencia;
    }

    public void setHojaDelincuencia(boolean hojaDelincuencia) {
        this.hojaDelincuencia = hojaDelincuencia;
    }

    @Basic
    @Column(name = "estrellas", nullable = false)
    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    @Basic
    @Column(name = "justificacion", nullable = false)
    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    @Basic
    @Column(name = "vence_licencia", nullable = false)
    public Timestamp getVence_licencia() {
        return vence_licencia;
    }

    public void setVence_licencia(Timestamp vence_licencia) {
        this.vence_licencia = vence_licencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxistaEntidad that = (TaxistaEntidad) o;
        return Objects.equals(pkCorreoUsuario, that.pkCorreoUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreoUsuario);
    }

    @OneToOne
    @JoinColumn(name = "pk_correo_usuario", referencedColumnName = "pk_correo", nullable = false)
    public UsuarioEntidad getUsuarioByPkCorreoUsuario() {
        return usuarioByPkCorreoUsuario;
    }

    public void setUsuarioByPkCorreoUsuario(UsuarioEntidad usuarioByPkCorreoUsuario) {
        this.usuarioByPkCorreoUsuario = usuarioByPkCorreoUsuario;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "taxi_actual")
    public TaxiEntidad getTaxiActual() {
        return taxiActual;
    }

    public void setTaxiActual(TaxiEntidad taxiActual) {
        this.taxiActual = taxiActual;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "duennoTaxi")
    public Collection<TaxiEntidad> getTaxiPropiedad() {
        return taxiPropiedad;
    }

    public void setTaxiPropiedad(Collection<TaxiEntidad> taxiPropiedad) {
        this.taxiPropiedad = taxiPropiedad;
    }

    @OneToMany(mappedBy = "taxistaByCorreoTaxi")
    public Collection<ViajeEntidad> getViajesByPkCorreoUsuario() {
        return viajesByPkCorreoUsuario;
    }

    public void setViajesByPkCorreoUsuario(Collection<ViajeEntidad> viajesByPkCorreoUsuario) {
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
    }

    @OneToMany(mappedBy = "taxistaConduce")
    public Collection<ConduceEntidad> getTaxisConducidos() {
        return taxisConducidos;
    }

    public void setTaxisConducidos(Collection<ConduceEntidad> taxisConducidos) {
        this.taxisConducidos = taxisConducidos;
    }
}
