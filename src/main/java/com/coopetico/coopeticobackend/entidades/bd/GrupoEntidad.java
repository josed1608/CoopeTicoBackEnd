package com.coopetico.coopeticobackend.entidades.bd;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "grupo", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkId")
public class GrupoEntidad {
    private String pkId;
    private Collection<PermisosGrupoEntidad> permisosGruposByPkId;
    private Collection<UsuarioEntidad> usuariosByPkId;

    public GrupoEntidad(String pkId, Collection<PermisosGrupoEntidad> permisosGruposByPkId, Collection<UsuarioEntidad> usuariosByPkId) {
        this.pkId = pkId;
        this.permisosGruposByPkId = permisosGruposByPkId;
        this.usuariosByPkId = usuariosByPkId;
    }

    public GrupoEntidad() {
    }

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
