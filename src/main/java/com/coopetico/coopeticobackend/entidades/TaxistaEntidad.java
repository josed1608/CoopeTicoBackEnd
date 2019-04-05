package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
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
    private UsuarioEntidad usuarioByPkCorreoUsuario;
    private TaxiEntidad taxiByPlacaTaxiManeja;
    private TaxiEntidad taxiByPlacaTaxiDueno;
    private Collection<ViajeEntidad> viajesByPkCorreoUsuario;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxistaEntidad that = (TaxistaEntidad) o;
        return estado == that.estado &&
                hojaDelincuencia == that.hojaDelincuencia &&
                estrellas == that.estrellas &&
                Objects.equals(pkCorreoUsuario, that.pkCorreoUsuario) &&
                Objects.equals(faltas, that.faltas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreoUsuario, faltas, estado, hojaDelincuencia, estrellas);
    }

    @OneToOne
    @JoinColumn(name = "pk_correo_usuario", referencedColumnName = "pk_correo", nullable = false)
    public UsuarioEntidad getUsuarioByPkCorreoUsuario() {
        return usuarioByPkCorreoUsuario;
    }

    public void setUsuarioByPkCorreoUsuario(UsuarioEntidad usuarioByPkCorreoUsuario) {
        this.usuarioByPkCorreoUsuario = usuarioByPkCorreoUsuario;
    }

    @ManyToOne
    @JoinColumn(name = "placa_taxi_maneja", referencedColumnName = "pk_placa", nullable = false)
    public TaxiEntidad getTaxiByPlacaTaxiManeja() {
        return taxiByPlacaTaxiManeja;
    }

    public void setTaxiByPlacaTaxiManeja(TaxiEntidad taxiByPlacaTaxiManeja) {
        this.taxiByPlacaTaxiManeja = taxiByPlacaTaxiManeja;
    }

    @ManyToOne
    @JoinColumn(name = "placa_taxi_dueno", referencedColumnName = "pk_placa")
    public TaxiEntidad getTaxiByPlacaTaxiDueno() {
        return taxiByPlacaTaxiDueno;
    }

    public void setTaxiByPlacaTaxiDueno(TaxiEntidad taxiByPlacaTaxiDueno) {
        this.taxiByPlacaTaxiDueno = taxiByPlacaTaxiDueno;
    }

    @OneToMany(mappedBy = "taxistaByCorreoTaxi")
    public Collection<ViajeEntidad> getViajesByPkCorreoUsuario() {
        return viajesByPkCorreoUsuario;
    }

    public void setViajesByPkCorreoUsuario(Collection<ViajeEntidad> viajesByPkCorreoUsuario) {
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
    }
}
