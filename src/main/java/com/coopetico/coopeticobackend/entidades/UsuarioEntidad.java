package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkCorreo")
public class UsuarioEntidad {
    private String pkCorreo;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String contrasena;
    private String foto;
    private ClienteEntidad clienteByPkCorreo;
    private CoopeticoEntidad coopeticoByPkCorreo;
    private TaxistaEntidad taxistaByPkCorreo;
    private GrupoEntidad grupoByIdGrupo;

    public UsuarioEntidad(String pkCorreo, String nombre, String apellidos, String telefono, String contrasena, String foto, ClienteEntidad clienteByPkCorreo, CoopeticoEntidad coopeticoByPkCorreo, TaxistaEntidad taxistaByPkCorreo, GrupoEntidad grupoByIdGrupo) {
        this.pkCorreo = pkCorreo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.foto = foto;
        this.clienteByPkCorreo = clienteByPkCorreo;
        this.coopeticoByPkCorreo = coopeticoByPkCorreo;
        this.taxistaByPkCorreo = taxistaByPkCorreo;
        this.grupoByIdGrupo = grupoByIdGrupo;
    }

    public UsuarioEntidad() {
    }

    @Id
    @Column(name = "pk_correo", nullable = false, length = 64)
    public String getPkCorreo() {
        return pkCorreo;
    }

    public void setPkCorreo(String pkCorreo) {
        this.pkCorreo = pkCorreo;
    }

    @Basic
    @Column(name = "nombre", nullable = false, length = 32)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellidos", nullable = false, length = 64)
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Basic
    @Column(name = "telefono", nullable = false, length = 8)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "contrasena", nullable = false, length = 128)
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Basic
    @Column(name = "foto", length = 512)
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntidad that = (UsuarioEntidad) o;
        return Objects.equals(pkCorreo, that.pkCorreo) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellidos, that.apellidos) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(contrasena, that.contrasena) &&
                Objects.equals(foto, that.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreo, nombre, apellidos, telefono, contrasena, foto);
    }

    @OneToOne(mappedBy = "usuarioByPkCorreoUsuario")
    public ClienteEntidad getClienteByPkCorreo() {
        return clienteByPkCorreo;
    }

    public void setClienteByPkCorreo(ClienteEntidad clienteByPkCorreo) {
        this.clienteByPkCorreo = clienteByPkCorreo;
    }

    @OneToOne(mappedBy = "usuarioByPkCorreoUsuario")
    public CoopeticoEntidad getCoopeticoByPkCorreo() {
        return coopeticoByPkCorreo;
    }

    public void setCoopeticoByPkCorreo(CoopeticoEntidad coopeticoByPkCorreo) {
        this.coopeticoByPkCorreo = coopeticoByPkCorreo;
    }

    @OneToOne(mappedBy = "usuarioByPkCorreoUsuario")
    public TaxistaEntidad getTaxistaByPkCorreo() {
        return taxistaByPkCorreo;
    }

    public void setTaxistaByPkCorreo(TaxistaEntidad taxistaByPkCorreo) {
        this.taxistaByPkCorreo = taxistaByPkCorreo;
    }

    @ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "pk_id", nullable = false)
    public GrupoEntidad getGrupoByIdGrupo() {
        return grupoByIdGrupo;
    }

    public void setGrupoByIdGrupo(GrupoEntidad grupoByIdGrupo) {
        this.grupoByIdGrupo = grupoByIdGrupo;
    }
}
