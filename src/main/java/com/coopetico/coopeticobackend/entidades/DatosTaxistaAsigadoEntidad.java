//-----------------------------------------------------------------------------
// Packete.
package com.coopetico.coopeticobackend.entidades;
//-----------------------------------------------------------------------------
// Imports.
//-----------------------------------------------------------------------------

/**
 * Clase de temporal de datos del taxista.
 * Se envían los datos que han estado saltando de endpoint en endpoint
 * durante el proceso de solicitud de viaje y los datos del taxista asignado
 * en función unicamente del correo del taxista y no ningún otro dato.
 *
 * @author Joseph Rementeria (b5582 fd4)
 * @since 14-05-2019
 */
public class DatosTaxistaAsigadoEntidad {
    //-------------------------------------------------------------------------
    // Variables globales
    private ViajeComenzandoEntidad viaje;
    private String correoTaxista;
    private String nombreTaxista;
    private String fotoTaxista;
    private float estrellasTaxista;
    //-------------------------------------------------------------------------

    /**
     *
     * Constructor con todos los parmetros
     *
     * @param viaje
     * @param correoTaxista correo del taxista asignado.
     * @param nombreTaxista nombre y apellidos del taxista concatenados
     * @param fotoTaxista la foto del taxista
     * @param estrellasTaxista el promedio de estrellas de un taxista
     */
    public DatosTaxistaAsigadoEntidad(
        ViajeComenzandoEntidad viaje,
        String correoTaxista,
        String nombreTaxista,
        String fotoTaxista,
        float estrellasTaxista
    ) {
        this.viaje = viaje;
        this.correoTaxista = correoTaxista;
        this.nombreTaxista = nombreTaxista;
        this.fotoTaxista = fotoTaxista;
        this.estrellasTaxista = estrellasTaxista;
    }

    public DatosTaxistaAsigadoEntidad() {
    }
    //-------------------------------------------------------------------------
    // setters and getter autogenerated by MS VSC (Windows version).


    public ViajeComenzandoEntidad getViaje() {
        return viaje;
    }

    public void setViaje(ViajeComenzandoEntidad viaje) {
        this.viaje = viaje;
    }

    public String getCorreoTaxista() {
        return correoTaxista;
    }

    public void setCorreoTaxista(String correoTaxista) {
        this.correoTaxista = correoTaxista;
    }

    public String getNombreTaxista() {
        return nombreTaxista;
    }

    public void setNombreTaxista(String nombreTaxista) {
        this.nombreTaxista = nombreTaxista;
    }

    public String getFotoTaxista() {
        return fotoTaxista;
    }

    public void setFotoTaxista(String fotoTaxista) {
        this.fotoTaxista = fotoTaxista;
    }

    public float getEstrellasTaxista() {
        return estrellasTaxista;
    }

    public void setEstrellasTaxista(float estrellasTaxista) {
        this.estrellasTaxista = estrellasTaxista;
    }
    //-------------------------------------------------------------------------
}