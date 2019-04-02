package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coopetico", schema = "coopetico-dev")
public class CoopeticoEntidad {
    private String pkCorreoUsuario;
    private UsuarioEntidad usuarioByPkCorreoUsuario;

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
        CoopeticoEntidad that = (CoopeticoEntidad) o;
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
}
