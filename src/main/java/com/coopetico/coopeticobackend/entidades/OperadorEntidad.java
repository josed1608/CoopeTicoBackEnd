package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "operador", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkCorreoUsuario")
public class OperadorEntidad {
    private String pkCorreoUsuario;
    private UsuarioEntidad usuarioByPkCorreoUsuario;
    private Collection<ViajeEntidad> viajesByPkCorreoUsuario;

    public OperadorEntidad(String pkCorreoUsuario, UsuarioEntidad usuarioByPkCorreoUsuario, Collection<ViajeEntidad> viajesByPkCorreoUsuario) {
        this.pkCorreoUsuario = pkCorreoUsuario;
        this.usuarioByPkCorreoUsuario = usuarioByPkCorreoUsuario;
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
    }

    public OperadorEntidad() {
    }

    @Id
    @Column(name = "pk_correo_usuario", nullable = false, length = 64)
    public String getPkCorreoUsuario() {
        return pkCorreoUsuario;
    }

    public void setPkCorreoUsuario(String pkCorreoUsuario) {
        this.pkCorreoUsuario = pkCorreoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperadorEntidad that = (OperadorEntidad) o;
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

    @OneToMany(mappedBy = "agendaOperador")
    public Collection<ViajeEntidad> getViajesByPkCorreoUsuario() {
        return viajesByPkCorreoUsuario;
    }

    public void setViajesByPkCorreoUsuario(Collection<ViajeEntidad> viajesByPkCorreoUsuario) {
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
    }
}
