package com.coopetico.coopeticobackend.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "coopetico-dev")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkCorreo")
public class UsuarioEntidad {

    private String pkCorreo;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;
    private String contrasena;
    private String foto;
    private boolean valid;

    private ClienteEntidad clienteByPkCorreo;
    private OperadorEntidad coopeticoByPkCorreo;
    private TaxistaEntidad taxistaByPkCorreo;
    private GrupoEntidad grupoByIdGrupo;

    public UsuarioEntidad(String pkCorreo, String nombre, String apellido1, String apellido2, String telefono, String contrasena, String foto, boolean valid, ClienteEntidad clienteByPkCorreo, OperadorEntidad coopeticoByPkCorreo, TaxistaEntidad taxistaByPkCorreo, GrupoEntidad grupoByIdGrupo) {
        this.pkCorreo = pkCorreo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.foto = foto;
        this.valid = valid;
        this.clienteByPkCorreo = clienteByPkCorreo;
        this.coopeticoByPkCorreo = coopeticoByPkCorreo;
        this.taxistaByPkCorreo = taxistaByPkCorreo;
        this.grupoByIdGrupo = grupoByIdGrupo;
    }

    public UsuarioEntidad() {
    }

    @NotEmpty
    @Email
    @Id
    @Column(name = "pk_correo", nullable = false, length = 64)
    public String getPkCorreo() {
        return pkCorreo;
    }

    public void setPkCorreo(String pkCorreo) {
        this.pkCorreo = pkCorreo;
    }

    @NotEmpty
    @Basic
    @Column(name = "nombre", nullable = false, length = 32)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotEmpty
    @Basic
    @Column(name = "apellido1", nullable = false, length = 64)
    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    @NotEmpty
    @Basic
    @Column(name = "apellido2", nullable = false, length = 64)
    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @NotEmpty
    @Size(min = 8, max = 8)
    @Basic
    @Column(name = "telefono", nullable = false, length = 8)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @NotEmpty
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

    @Basic
    @Column(name = "valid", length = 512)
    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntidad that = (UsuarioEntidad) o;
        return Objects.equals(pkCorreo, that.pkCorreo) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellido1, that.apellido1) &&
                Objects.equals(apellido2, that.apellido2) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(contrasena, that.contrasena) &&
                Objects.equals(foto, that.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkCorreo, nombre, apellido1, apellido2, telefono, contrasena, foto);
    }

    @OneToOne(mappedBy = "usuarioByPkCorreoUsuario")
    public ClienteEntidad getClienteByPkCorreo() {
        return clienteByPkCorreo;
    }

    public void setClienteByPkCorreo(ClienteEntidad clienteByPkCorreo) {
        this.clienteByPkCorreo = clienteByPkCorreo;
    }

    @OneToOne(mappedBy = "usuarioByPkCorreoUsuario")
    public OperadorEntidad getCoopeticoByPkCorreo() {
        return coopeticoByPkCorreo;
    }

    public void setCoopeticoByPkCorreo(OperadorEntidad coopeticoByPkCorreo) {
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

    /**
     * Autor: Joseph Rementería (b55824)
     * Fecha: 13/04/2019
     *
     * Sobre-escrritura del método toString de la clase ClienteEntidad
     * Se usa para tener ver los datos del pefil y los datos necesarios
     * para luego ser editador.
     *
     * @return una string con los datos de la entidad cliente en
     * formato JSON =.
     */

    @Override
    public String toString(){
        String result = "{";
        result += "\"correo\": \""          + this.pkCorreo;
        result += "\",\"nombre\": \""       + this.nombre;
        result += "\",\"apellido1\": \""    + this.apellido1;
        result += "\",\"apellido2\": \""    + this.apellido2;
        result += "\",\"telefono\": \""     + this.telefono;
        result += "\",\"foto\": \""         + this.foto;
        result += "\"}";
        return result;
    }

}
