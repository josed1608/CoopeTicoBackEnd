package com.coopetico.coopeticobackend.entidades;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "token_recuperacion_contrasena", schema = "coopetico-dev")
public class TokenRecuperacionContrasenaEntidad {
    private String fkCorreoUsuario;
    private String token;
    private Timestamp fechaExpiracion;

    @Id
    @Column(name = "fk_correo_usuario")
    public String getFkCorreoUsuario() {
        return fkCorreoUsuario;
    }

    public void setFkCorreoUsuario(String fkCorreoUsuario) {
        this.fkCorreoUsuario = fkCorreoUsuario;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "fecha_expiracion")
    public Timestamp getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Timestamp fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRecuperacionContrasenaEntidad that = (TokenRecuperacionContrasenaEntidad) o;
        return Objects.equals(fkCorreoUsuario, that.fkCorreoUsuario) &&
                Objects.equals(token, that.token) &&
                Objects.equals(fechaExpiracion, that.fechaExpiracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkCorreoUsuario, token, fechaExpiracion);
    }
}
