package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permisos_grupo", schema = "coopetico-dev", catalog = "")
@IdClass(PermisosGrupoEntidadPK.class)
public class PermisosGrupoEntidad {
    private int pkIdPermisos;
    private String pkIdGrupo;
    private PermisoEntidad permisoByPkIdPermisos;
    private GrupoEntidad grupoByPkIdGrupo;

    @Id
    @Column(name = "pk_id_permisos", nullable = false)
    public int getPkIdPermisos() {
        return pkIdPermisos;
    }

    public void setPkIdPermisos(int pkIdPermisos) {
        this.pkIdPermisos = pkIdPermisos;
    }

    @Id
    @Column(name = "pk_id_grupo", nullable = false, length = 32)
    public String getPkIdGrupo() {
        return pkIdGrupo;
    }

    public void setPkIdGrupo(String pkIdGrupo) {
        this.pkIdGrupo = pkIdGrupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisosGrupoEntidad that = (PermisosGrupoEntidad) o;
        return pkIdPermisos == that.pkIdPermisos &&
                Objects.equals(pkIdGrupo, that.pkIdGrupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkIdPermisos, pkIdGrupo);
    }

    @ManyToOne
    @JoinColumn(name = "pk_id_permisos", referencedColumnName = "pk_id", nullable = false)
    public PermisoEntidad getPermisoByPkIdPermisos() {
        return permisoByPkIdPermisos;
    }

    public void setPermisoByPkIdPermisos(PermisoEntidad permisoByPkIdPermisos) {
        this.permisoByPkIdPermisos = permisoByPkIdPermisos;
    }

    @ManyToOne
    @JoinColumn(name = "pk_id_grupo", referencedColumnName = "pk_id", nullable = false)
    public GrupoEntidad getGrupoByPkIdGrupo() {
        return grupoByPkIdGrupo;
    }

    public void setGrupoByPkIdGrupo(GrupoEntidad grupoByPkIdGrupo) {
        this.grupoByPkIdGrupo = grupoByPkIdGrupo;
    }
}
