package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permiso", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkId")
public class PermisoEntidad {
    private int pkId;
    private String descripcion;
    @JsonIgnore
    private Collection<PermisosGrupoEntidad> permisosGruposByPkId;

    public PermisoEntidad(int pkId, String descripcion, Collection<PermisosGrupoEntidad> permisosGruposByPkId) {
        this.pkId = pkId;
        this.descripcion = descripcion;
        this.permisosGruposByPkId = permisosGruposByPkId;
    }

    public PermisoEntidad() {
    }

    @Id
    @Column(name = "pk_id", nullable = false)
    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    @Basic
    @Column(name = "descripcion", nullable = false, length = 64)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisoEntidad that = (PermisoEntidad) o;
        return pkId == that.pkId &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkId, descripcion);
    }

    @OneToMany(mappedBy = "permisoByPkIdPermisos")
    public Collection<PermisosGrupoEntidad> getPermisosGruposByPkId() {
        return permisosGruposByPkId;
    }

    public void setPermisosGruposByPkId(Collection<PermisosGrupoEntidad> permisosGruposByPkId) {
        this.permisosGruposByPkId = permisosGruposByPkId;
    }
}
