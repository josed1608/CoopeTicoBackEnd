package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permisos_grupo", schema = "coopetico-dev")
public class PermisosGrupoEntidad {
    private PermisosGrupoEntidadPK permisosGrupoEntidadPK;
    private PermisoEntidad permisoByPkIdPermisos;
    private GrupoEntidad grupoByPkIdGrupo;

    @EmbeddedId
    public PermisosGrupoEntidadPK getPermisosGrupoEntidadPK() {
        return permisosGrupoEntidadPK;
    }

    public void setPermisosGrupoEntidadPK(PermisosGrupoEntidadPK permisosGrupoEntidadPK) {
        this.permisosGrupoEntidadPK = permisosGrupoEntidadPK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisosGrupoEntidad that = (PermisosGrupoEntidad) o;
        return Objects.equals(permisosGrupoEntidadPK, that.permisosGrupoEntidadPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permisosGrupoEntidadPK);
    }

    @ManyToOne
    @MapsId("pkIdPermisos")
    @JoinColumn(name = "pk_id_permisos", referencedColumnName = "pk_id", nullable = false)
    public PermisoEntidad getPermisoByPkIdPermisos() {
        return permisoByPkIdPermisos;
    }

    public void setPermisoByPkIdPermisos(PermisoEntidad permisoByPkIdPermisos) {
        this.permisoByPkIdPermisos = permisoByPkIdPermisos;
    }

    @ManyToOne
    @MapsId("pkIdGrupo")
    @JoinColumn(name = "pk_id_grupo", referencedColumnName = "pk_id", nullable = false)
    public GrupoEntidad getGrupoByPkIdGrupo() {
        return grupoByPkIdGrupo;
    }

    public void setGrupoByPkIdGrupo(GrupoEntidad grupoByPkIdGrupo) {
        this.grupoByPkIdGrupo = grupoByPkIdGrupo;
    }
}
