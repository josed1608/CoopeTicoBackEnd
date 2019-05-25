package com.coopetico.coopeticobackend.entidades;

/**
 * Clase que almacena la información de interes de un taxi
 */
public class TaxiTemporal {
    // Placa del taxi
    String placa;
    // Ubicacion latitud del taxi
    double latitud;
    // Ubicacion longitud del taxi
    double longitud;
    // Clase del taxi A,B,C
    String clase;
    // Disponibilidad del taxi
    boolean disponible;
    // Si tiene datafono mastercard el taxi
    boolean datafonoMastercard;

    /**
     * Constructor por defecto
     */

    public TaxiTemporal(){}
    /**
     * Constructor de la clase
     * @param placa Identificador del taxi
     * @param latitud Ubicacion del taxis
     * @param longitud Ubicacion del taxis
     * @param clase Clase del taxi A,B,C
     * @param disponible Si el taxi está disponible
     * @param datafonoMastercard Si permite pagos de tarjeta mastercard
     */
    public TaxiTemporal(String placa, double latitud, double longitud, String clase, boolean disponible, boolean datafonoMastercard) {
        this.placa = placa;
        this.latitud = latitud;
        this.longitud = longitud;
        this.clase = clase;
        this.disponible = disponible;
        this.datafonoMastercard = datafonoMastercard;
    }

    /**
     * Permite obtener la placa de un taxi
     * @return Placa de un taxi
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Permite setear el valor de la placa del taxi
     * @param placa Valor nuevo
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Permite obtener la latitud del taxi
     * @return Latitud del taxi
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Permite cambiar el valor de la latitud del taxi
     * @param latitud Valor nuevo
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Permite obtener la ubicacion longitud del taxi
     * @return Ubicacion la longitud del taxi
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Permite modificar el valor de la ubicacion del taxi en longitud
     * @param longitud Nuevo valor
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Permite obtener la clase de un taxi
     * @return Clase de un taxi
     */
    public String getClase() {
        return clase;
    }

    /**
     * Permite modificar el valor de la clase de un taxi
     * @param clase Nueva clase
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * Permite obtener el estado de un taxi
     * @return Estado de un taxi
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Permite modificar el estado de un taxi
     * @param disponible Nuevo estado
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Permite obtener si un taxi tiene datafono
     * @return Valor del datafono
     */
    public boolean isDatafonoMastercard() {
        return datafonoMastercard;
    }

    /**
     * Permite modificar el valor del datafono del taxi
     * @param datafonoMastercard Nuevo valor
     */
    public void setDatafonoMastercard(boolean datafonoMastercard) {
        this.datafonoMastercard = datafonoMastercard;
    }
}
