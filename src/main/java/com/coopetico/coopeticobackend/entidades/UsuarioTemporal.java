package com.coopetico.coopeticobackend.entidades;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTemporal {
    private String correo;
    private String nombre;
    private String apellidos;
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
        this.apellidos = usuarioEntidad.getApellidos();
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
        usuarioEntidad.setApellidos(this.apellidos);
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
