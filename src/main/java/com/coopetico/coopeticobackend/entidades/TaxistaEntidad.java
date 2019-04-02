package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "taxista", schema = "coopetico-dev", catalog = "")
public class TaxistaEntidad {
    private String pkCorreoUsuario;
    private Object faltas;
    private boolean estado;
    private boolean hojaDelincuencia;
    private int estrellas;
    private Collection<AutenticaEntidad> autenticasByPkCorreoUsuario;
    private UsuarioEntidad usuarioByPkCorreoUsuario;

    @Id
    @Column(name = "pk_correo_usuario", nullable = false, length = 64)
    public String getPkCorreoUsuario() {
        return pkCorreoUsuario;
    }

    public void setPkCorreoUsuario(String pkCorreoUsuario) {
        this.pkCorreoUsuario = pkCorreoUsuario;
    }

    @Basic
    @Column(name = "faltas", nullable = false)
    public Object getFaltas() {
        return faltas;
    }

    public void setFaltas(Object faltas) {
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
    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
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

    @OneToMany(mappedBy = "taxistaByPkCorreoTaxista")
    public Collection<AutenticaEntidad> getAutenticasByPkCorreoUsuario() {
        return autenticasByPkCorreoUsuario;
    }

    public void setAutenticasByPkCorreoUsuario(Collection<AutenticaEntidad> autenticasByPkCorreoUsuario) {
        this.autenticasByPkCorreoUsuario = autenticasByPkCorreoUsuario;
    }

    @OneToOne
    @JoinColumn(name = "pk_correo_usuario", referencedColumnName = "pk_correo", nullable = false)
    public UsuarioEntidad getUsuarioByPkCorreoUsuario() {
        return usuarioByPkCorreoUsuario;
    }

    public void setUsuarioByPkCorreoUsuario(UsuarioEntidad usuarioByPkCorreoUsuario) {
        this.usuarioByPkCorreoUsuario = usuarioByPkCorreoUsuario;
    }
}
