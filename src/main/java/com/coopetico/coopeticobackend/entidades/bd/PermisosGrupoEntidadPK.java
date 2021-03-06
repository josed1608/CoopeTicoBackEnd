package com.coopetico.coopeticobackend.entidades.bd;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PermisosGrupoEntidadPK implements Serializable {
    private int pkIdPermisos;
    private String pkIdGrupo;

    @Column(name = "pk_id_permisos", nullable = false)
    public int getPkIdPermisos() {
        return pkIdPermisos;
    }

    public void setPkIdPermisos(int pkIdPermisos) {
        this.pkIdPermisos = pkIdPermisos;
    }

    @Column(name = "pk_id_grupo", nullable = false, length = 32)
    public String getPkIdGrupo() {
        return pkIdGrupo;
    }

    public void setPkIdGrupo(String pkIdGrupo) {
        this.pkIdGrupo = pkIdGrupo;
    }

    public PermisosGrupoEntidadPK(int pkIdPermisos, String pkIdGrupo) {
        this.pkIdPermisos = pkIdPermisos;
        this.pkIdGrupo = pkIdGrupo;
    }

    public PermisosGrupoEntidadPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisosGrupoEntidadPK that = (PermisosGrupoEntidadPK) o;
        return pkIdPermisos == that.pkIdPermisos &&
                Objects.equals(pkIdGrupo, that.pkIdGrupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkIdPermisos, pkIdGrupo);
    }
}
