package com.coopetico.coopeticobackend.entidades;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTemporal {
    private String correo;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;
    private String contrasena;
    private String foto;
    //private String clienteByPkCorreo;
    //private String coopeticoByPkCorreo;
    //private String taxistaByPkCorreo;
    private String idGrupo;


    public UsuarioTemporal(){}

    public UsuarioTemporal(UsuarioEntidad usuarioEntidad){
        this.correo = usuarioEntidad.getPkCorreo();
        this.nombre = usuarioEntidad.getNombre();
        this.apellido1 = usuarioEntidad.getApellido1();
        this.apellido2 = usuarioEntidad.getApellido2();
        this.telefono = usuarioEntidad.getTelefono();
        this.foto = usuarioEntidad.getFoto();
        this.contrasena = usuarioEntidad.getContrasena();
        this.idGrupo = usuarioEntidad.getGrupoByIdGrupo().getPkId();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<UsuarioTemporal> getListaUsuarioTemporal(List<UsuarioEntidad> usuarios){
        List<UsuarioTemporal> listaTemporal = new ArrayList<>();
        for (UsuarioEntidad usuario : usuarios) {
            listaTemporal.add(new UsuarioTemporal(usuario));
        }
        return listaTemporal;
    }

    public UsuarioEntidad convertirAUsuarioEntidad(){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setTelefono(this.telefono);
        usuarioEntidad.setNombre(this.nombre);
        usuarioEntidad.setApellido1(this.apellido1);
        usuarioEntidad.setApellido2(this.apellido2);
        usuarioEntidad.setContrasena(this.contrasena);
        usuarioEntidad.setPkCorreo(this.correo);
        usuarioEntidad.setFoto(this.foto);

        GrupoEntidad grupo = new GrupoEntidad();
        grupo.setPkId(this.getIdGrupo());
        usuarioEntidad.setGrupoByIdGrupo(grupo);
        usuarioEntidad.setClienteByPkCorreo(null);
        usuarioEntidad.setCoopeticoByPkCorreo(null);
        usuarioEntidad.setTaxistaByPkCorreo(null);

        return usuarioEntidad;
    }
}
