package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "coopetico-dev")
public class ClienteEntidad {
    private String pkCorreoUsuario;
    private UsuarioEntidad usuarioByPkCorreoUsuario;
    private Collection<ViajeEntidad> viajesByPkCorreoUsuario;

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
        ClienteEntidad that = (ClienteEntidad) o;
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

    @OneToMany(mappedBy = "clienteByPkCorreoCliente")
    public Collection<ViajeEntidad> getViajesByPkCorreoUsuario() {
        return viajesByPkCorreoUsuario;
    }

    public void setViajesByPkCorreoUsuario(Collection<ViajeEntidad> viajesByPkCorreoUsuario) {
        this.viajesByPkCorreoUsuario = viajesByPkCorreoUsuario;
    }
}
