package com.coopetico.coopeticobackend.entidades;

import java.io.Serializable;

/**
 * Clase para representar el request que el correo del taxista y la ubicación que se va a actualizar.
 * @author Marco Venegas
 */
public class UbicacionTaxistaEntidad implements Serializable {

    private String correoTaxista;
    private double latitud;
    private double longitud;

    public String getCorreoTaxista() {
        return correoTaxista;
    }

    public void setCorreoTaxista(String correoTaxista) {
        this.correoTaxista = correoTaxista;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


    public UbicacionTaxistaEntidad(String correoTaxista, double latitud, double longitud) {
        this.correoTaxista = correoTaxista;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}

