package com.coopetico.coopeticobackend.entidades;

import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;

import java.sql.Timestamp;
import java.util.List;

/**
 Clase temporal para comunicacion con el frontend de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       17-04-2019.
 @version    1.0.
 */
public class TaxistaEntidadTemporal {

    /**
     * Correo del taxista.
     */
    private String pkCorreoUsuario;

    /**
     * Faltas del taxista.
     */
    private String faltas;

    /**
     * Estado del taxista.
     */
    private boolean estado;

    /**
     * Hoja de delincuencia del taxista.
     */
    private boolean hojaDelincuencia;

    /**
     * Promedio de estrellas del taxista.
     */
    private float estrellas;

    /**
     * Nombre del taxista.
     */
    private String nombre;

    /**
     * Apellido1 del taxista.
     */
    private String apellido1;

    /**
     * Apellido2 del taxista.
     */
    private String apellido2;

    /**
     * Telefono del taxista.
     */
    private String telefono;

    /**
     * Foto del taxista.
     */
    private String foto;

    /**
     * Bit de borrado logico. Valido
     */
    private Boolean valid;

    /**
     * Justificacion para desactivar taxista.
     */
    private String justificacion;

    /**
     * Fecha de vencimiento del taxista.
     */
    private Timestamp vence_licencia;

    /**
     * Vector de los placas de los taxis que puede conducir.
     */
    private List<String> siConduce;

    /**
     * Vector de los placas de los taxis que no puede conducir.
     */
    private List<String> noConduce;

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    /**
     * Funcion que retorna el correo del taxista.
     * @return Correo del taxista.
     */
    public String getPkCorreoUsuario() {
        return pkCorreoUsuario;
    }

    /**
     * Funcion que retorna las faltas del taxista.
     * @return Faltas del taxista.
     */
    public String getFaltas() {
        return faltas;
    }

    /**
     * Funcion que retorna el estado del taxista.
     * @return Estado del taxista.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Funcion que retorna si se tiene la hoja de delincuencia del taxista.
     * @return Verdadero si se tiene la hoja de delincuencia del taxista.
     */
    public boolean isHojaDelincuencia() {
        return hojaDelincuencia;
    }

    /**
     * Funcion que retorna el promedio de las estrellas del taxista.
     * @return Promedio de las estrellas del taxista.
     */
    public float getEstrellas() {
        return estrellas;
    }

    /**
     * Funcion que retorna el nombre del taxista.
     * @return Nombre del taxista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Funcion que retorna los apellidos del taxista.
     * @return Apellidos del taxista.
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Funcion que retorna los apellidos del taxista.
     * @return Apellidos del taxista.
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Funcion que retorna el telefono del taxista.
     * @return Telefono del taxista.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Funcion que retorna la foto del taxista.
     * @return Foto del taxista.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Funcion que retorna el bit de valido del taxista.
     * @return Valido
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * Funcion que retorna la justificacion del taxista.
     * @return Justificacion del taxista.
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * Funcion que retorna la justificacion de desactivacion del taxista.
     * @return Fecha de vencimiento de la licencia del taxista.
     */
    public Timestamp getVence_licencia() {
        return vence_licencia;
    }

    /**
     * Funcion que retorna los taxis que puede conducir un taxista.
     * @return Vector de placas de los taxis que puede manejar el taxista.
     */
    public List<String> getSiConduce() {
        return siConduce;
    }

    /**
     * Funcion que retorna los taxis que no puede conducir un taxista.
     * @return Vector de placas de los taxis que no puede manejar el taxista.
     */
    public List<String> getNoConduce() {
        return noConduce;
    }

    /**
     * Metodo que modifica el correo del taxista.
     * @param pkCorreoUsuario Nuevo correo que se quiere guardar.
     */
    public void setPkCorreoUsuario(String pkCorreoUsuario) {
        this.pkCorreoUsuario = pkCorreoUsuario;
    }

    /**
     * Metodo que modifica las faltas del taxista.
     * @param faltas Faltasa que se quiere guardar.
     */
    public void setFaltas(String faltas) {
        this.faltas = faltas;
    }

    /**
     * Metodo que modifica el estado del taxista.
     * @param estado Nuevo estado que se quiere guardar.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Metodo que modifica el valor de la hoja de delincuencia del taxista.
     * @param hojaDelincuencia Nuevo estado de la hoha de delincuenca que se quiere guardar.
     */
    public void setHojaDelincuencia(boolean hojaDelincuencia) {
        this.hojaDelincuencia = hojaDelincuencia;
    }

    /**
     * Metodo que modifica el promedio de las estrellas del taxista.
     * @param estrellas Nuevo promedio de estrellas que se quiere guardar.
     */
    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * Metodo que modifica el nombre del taxista.
     * @param nombre Nuevo nombre que se quiere guardar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que modifica los apellidos del taxista.
     * @param apellido1 Nuevos apellidos que se quiere guardar.
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Metodo que modifica los apellidos del taxista.
     * @param apellido2 Nuevos apellidos que se quiere guardar.
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Metodo que modifica el telefono del taxista.
     * @param telefono Nuevo telefono que se quiere guardar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo que modifica la direccion de la foto del taxista.
     * @param foto Nueva direccion de la foto que se quiere guardar.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Metodo que modifica el bit de valido del taxista.
     * @param valid Nuevo bit de valido
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * Metodo que modifica la fecha de vencimiento de la licencia del taxista.
     * @param vence_licencia Nueva fecha de vencimiento de licencia que se quiere guardar.
     */
    public void setVence_licencia(Timestamp vence_licencia) {
        this.vence_licencia = vence_licencia;
    }

    /**
     * Metodo que modifica los taxis que puede manejar un taxista.
     * @param siConduce Nueva lista de taxis que puede manejar un taxista.
     */
    public void setSiConduce(List<String> siConduce) {
        this.siConduce = siConduce;
    }

    /**
     * Metodo que modifica los taxis que no puede manejar un taxista.
     * @param noConduce Nueva lista de taxis que no puede manejar un taxista.
     */
    public void setNoConduce(List<String> noConduce) {
        this.noConduce = noConduce;
    }

    /**
     * Constructor para convertir de TaxistaEntidad a TaxistaEntidadTemporal.
     * @param taxista TaxistaEntidad que contiene los datos que se quiere pasar a esta nueva instancia.
     * @param noConduce Placas de los taxis que no conduce ese taxista.
     */
    public TaxistaEntidadTemporal(TaxistaEntidad taxista, List<String> siConduce, List<String> noConduce){
        this.pkCorreoUsuario = taxista.getPkCorreoUsuario();
        this.faltas = taxista.getFaltas();
        this.estado = taxista.isEstado();
        this.hojaDelincuencia = taxista.isHojaDelincuencia();
        this.estrellas = taxista.getEstrellas();
        this.justificacion = taxista.getPkCorreoUsuario();
        this.vence_licencia = taxista.getVence_licencia();
        this.nombre = taxista.getUsuarioByPkCorreoUsuario().getNombre();
        this.apellido1 = taxista.getUsuarioByPkCorreoUsuario().getApellido1();
        this.apellido2 = taxista.getUsuarioByPkCorreoUsuario().getApellido2();
        this.telefono = taxista.getUsuarioByPkCorreoUsuario().getTelefono();
        this.foto = taxista.getUsuarioByPkCorreoUsuario().getFoto();
        this.valid = taxista.getUsuarioByPkCorreoUsuario().getValid();

        this.siConduce = siConduce;
        this.noConduce = noConduce;
    }

    public TaxistaEntidadTemporal(TaxistaEntidad taxista){
        this.pkCorreoUsuario = taxista.getPkCorreoUsuario();
        this.faltas = taxista.getFaltas();
        this.estado = taxista.isEstado();
        this.hojaDelincuencia = taxista.isHojaDelincuencia();
        this.estrellas = taxista.getEstrellas();
        this.justificacion = taxista.getPkCorreoUsuario();
        this.vence_licencia = taxista.getVence_licencia();
        this.nombre = taxista.getUsuarioByPkCorreoUsuario().getNombre();
        this.apellido1 = taxista.getUsuarioByPkCorreoUsuario().getApellido1();
        this.apellido2 = taxista.getUsuarioByPkCorreoUsuario().getApellido2();
        this.telefono = taxista.getUsuarioByPkCorreoUsuario().getTelefono();
        this.foto = taxista.getUsuarioByPkCorreoUsuario().getFoto();
    }

    /**
     * Constructor.
     */
    public TaxistaEntidadTemporal(){
    }

}
