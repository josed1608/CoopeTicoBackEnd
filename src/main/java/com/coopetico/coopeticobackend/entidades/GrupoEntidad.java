package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "grupo", schema = "coopetico-dev")
public class GrupoEntidad {
    private String pkId;
    private Collection<PermisosGrupoEntidad> permisosGruposByPkId;
    private Collection<UsuarioEntidad> usuariosByPkId;

    @Id
    @Column(name = "pk_id", nullable = false, length = 32)
    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupoEntidad that = (GrupoEntidad) o;
        return Objects.equals(pkId, that.pkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkId);
    }

    @OneToMany(mappedBy = "grupoByPkIdGrupo")
    public Collection<PermisosGrupoEntidad> getPermisosGruposByPkId() {
        return permisosGruposByPkId;
    }

    public void setPermisosGruposByPkId(Collection<PermisosGrupoEntidad> permisosGruposByPkId) {
        this.permisosGruposByPkId = permisosGruposByPkId;
    }

    @OneToMany(mappedBy = "grupoByIdGrupo")
    public Collection<UsuarioEntidad> getUsuariosByPkId() {
        return usuariosByPkId;
    }

    public void setUsuariosByPkId(Collection<UsuarioEntidad> usuariosByPkId) {
        this.usuariosByPkId = usuariosByPkId;
    }
}