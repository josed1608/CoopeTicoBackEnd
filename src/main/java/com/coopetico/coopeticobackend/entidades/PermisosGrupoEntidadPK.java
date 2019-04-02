package com.coopetico.coopeticobackend.entidades;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PermisosGrupoEntidadPK implements Serializable {
    private int pkIdPermisos;
    private String pkIdGrupo;

    @Column(name = "pk_id_permisos", nullable = false)
    @Id
    public int getPkIdPermisos() {
        return pkIdPermisos;
    }

    public void setPkIdPermisos(int pkIdPermisos) {
        this.pkIdPermisos = pkIdPermisos;
    }

    @Column(name = "pk_id_grupo", nullable = false, length = 32)
    @Id
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
        PermisosGrupoEntidadPK that = (PermisosGrupoEntidadPK) o;
        return pkIdPermisos == that.pkIdPermisos &&
                Objects.equals(pkIdGrupo, that.pkIdGrupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkIdPermisos, pkIdGrupo);
    }
}
