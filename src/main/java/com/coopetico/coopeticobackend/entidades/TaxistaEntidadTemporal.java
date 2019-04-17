package com.coopetico.coopeticobackend.entidades;

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
     * Apellidos del taxista.
     */
    private String apellidos;

    /**
     * Telefono del taxista.
     */
    private String telefono;

    /**
     * Foto del taxista.
     */
    private String foto;

    /**
     * Placa del taxi que maneja el taxista.
     */
    private String pkPlaca;

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
    public String getApellidos() {
        return apellidos;
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
     * Funcion que retorna la placa del taxi que maneja el taxista.
     * @return Placa del taxi que maneja el taxista.
     */
    public String getPkPlaca() {
        return pkPlaca;
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
     * @param apellidos Nuevos apellidos que se quiere guardar.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
     * Metodo que modifica la placa del taxi que maneja el taxista.
     * @param pkPlaca Nueva placa del taxi que maneja el taxista que se quiere guardar.
     */
    public void setPkPlaca(String pkPlaca) {
        this.pkPlaca = pkPlaca;
    }

    /**
     * Constructor para convertir de TaxistaEntidad a TaxistaEntidadTemporal.
     * @param taxista TaxistaEntidad que contiene los datos que se quiere pasar a esta nueva instancia.
     */
    public TaxistaEntidadTemporal(TaxistaEntidad taxista){
        this.pkCorreoUsuario = taxista.getPkCorreoUsuario();
        this.faltas = taxista.getFaltas();
        this.estado = taxista.isEstado();
        this.hojaDelincuencia = taxista.isHojaDelincuencia();
        this.estrellas = taxista.getEstrellas();
        this.nombre = taxista.getUsuarioByPkCorreoUsuario().getNombre();
        this.apellidos = taxista.getUsuarioByPkCorreoUsuario().getApellidos();
        this.telefono = taxista.getUsuarioByPkCorreoUsuario().getTelefono();
        this.foto = taxista.getUsuarioByPkCorreoUsuario().getFoto();
        this.pkPlaca = taxista.getTaxiByPlacaTaxiManeja().getPkPlaca();
    }

    /**
     * Constructor.
     */
    public TaxistaEntidadTemporal(){
    }

}
