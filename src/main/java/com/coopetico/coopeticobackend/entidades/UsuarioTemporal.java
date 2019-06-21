package com.coopetico.coopeticobackend.entidades;

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de manejar la información importante de un taxi
 */
public class UsuarioTemporal {
    // Correo del usuario
    private String correo;
    // Nombre del usuario
    private String nombre;
    // Primer apellido del usuario
    private String apellido1;
    // Segundo apellido del usuario
    private String apellido2;
    // Telefono del usuario
    private String telefono;
    // Contrasenna del usuario
    private String contrasena;
    // Foto del usuario
    private String foto;
    // Grupo al que pertenece el usuario
    private String idGrupo;
    // Indica si está activo
    private boolean valid;


    /**
     * Constructor por defecto
     */
    public UsuarioTemporal(){}

    /**
     * Construye un UsuarioTemporal a partir de un UsuarioEntidad
     * @param usuarioEntidad
     */
    public UsuarioTemporal(UsuarioEntidad usuarioEntidad){
        this.correo = usuarioEntidad.getPkCorreo();
        this.nombre = usuarioEntidad.getNombre();
        this.apellido1 = usuarioEntidad.getApellido1();
        this.apellido2 = usuarioEntidad.getApellido2();
        this.telefono = usuarioEntidad.getTelefono();
        this.foto = usuarioEntidad.getFoto();
        this.contrasena = usuarioEntidad.getContrasena();
        this.idGrupo = usuarioEntidad.getGrupoByIdGrupo().getPkId();
        this.valid = usuarioEntidad.getValid();
    }

    /**
     * Metodo que permite obtener el correo
     * @return Correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Metodo que permite modificar el correo
     * @param correo Correo nuevo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Metodo que permite obtener el nombre
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que permite modificar el correo
     * @param nombre Nombre nuevo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que permite obtener el primer apellido
     * @return Primer apellido del usuario
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Metodo que permite modificar el apellido1
     * @param apellido1 Nuevo apellido1
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Metodo que permite obtener el segundo apellido
     * @return Segundo apellido del usuario
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Metodo que permite modificar el apellido2
     * @param apellido2 Nuevo apellido2
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Metodo que permite obtener el telefono
     * @return Telefono del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo que permite modificar el telefono
     * @param telefono Nuevo telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo que permite obtener la foto
     * @return Url de la foto del usuario
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Metodo que permite modificar la foto
     * @param foto Nueva foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Metodo que permite obtener la contrasenna
     * @return Constrasenna del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Metodo que permite modificar la contrasenna
     * @param contrasena Nueva contrasenna
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    /**
     * Metodo que permite obtener el grupo
     * @return Grupo al que pertenece el usuario
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Metodo que permite modificar el idGrupo
     * @param idGrupo Nuevo idGrupo
     */
    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Metodo que permite obtener si es válido
     * @return verdadero si es válido
     */
    public boolean getValid() {return valid;}

    /**
     * Permite modificar el valor valid
     * @param valid Indica si es válido o no
     */
    public void setValid(boolean valid){ this.valid = valid; }

    /**
     * Metodo que convierte una lista de UsuariosEntidad a una lista de UsuariosTemporal
     * @param usuarios Lista con los usuarios de la clase UsuarioEntidad
     * @return Lista con los usuarios de la clase UsuarioTemporal
     */
    public List<UsuarioTemporal> getListaUsuarioTemporal(List<UsuarioEntidad> usuarios){
        List<UsuarioTemporal> listaTemporal = new ArrayList<>();
        for (UsuarioEntidad usuario : usuarios) {
            listaTemporal.add(new UsuarioTemporal(usuario));
        }
        return listaTemporal;
    }

    /**
     * Se convierte los datos de la clase UsuarioTemporal en una clase UsuarioEntidad
     * @return
     */
    public UsuarioEntidad convertirAUsuarioEntidad(){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setTelefono(this.telefono);
        usuarioEntidad.setNombre(this.nombre);
        usuarioEntidad.setApellido1(this.apellido1);
        usuarioEntidad.setApellido2(this.apellido2);
        usuarioEntidad.setContrasena(this.contrasena);
        usuarioEntidad.setPkCorreo(this.correo);
        usuarioEntidad.setFoto(this.foto);
        usuarioEntidad.setValid(this.valid);

        GrupoEntidad grupo = new GrupoEntidad();
        grupo.setPkId(this.getIdGrupo());
        usuarioEntidad.setGrupoByIdGrupo(grupo);
        usuarioEntidad.setClienteByPkCorreo(null);
        usuarioEntidad.setCoopeticoByPkCorreo(null);
        usuarioEntidad.setTaxistaByPkCorreo(null);

        return usuarioEntidad;
    }
}
