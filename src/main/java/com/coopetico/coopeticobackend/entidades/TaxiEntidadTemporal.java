package com.coopetico.coopeticobackend.entidades;

import com.coopetico.coopeticobackend.entidades.bd.ConduceEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

public class TaxiEntidadTemporal {
    private String pkPlaca;
    private Boolean datafono;
    private String telefono;
    private String clase;
    private String tipo;
    private Timestamp fechaVenRtv;
    private Timestamp fechaVenMarchamo;
    private Timestamp fechaVenSeguro;
    private Boolean valido;
    private String foto;
    private String correoTaxista;
    private boolean estado;
    private String justificacion;

    public TaxiEntidadTemporal() {
    }

    public TaxiEntidadTemporal(String pkPlaca, Boolean datafono, String telefono, String clase, String tipo, Timestamp fechaVenRtv, Timestamp fechaVenMarchamo, Timestamp fechaVenSeguro, Boolean valido, String foto, String correoTaxista, boolean estado, String justificacion) {
        this.pkPlaca = pkPlaca;
        this.datafono = datafono;
        this.telefono = telefono;
        this.clase = clase;
        this.tipo = tipo;
        this.fechaVenRtv = fechaVenRtv;
        this.fechaVenMarchamo = fechaVenMarchamo;
        this.fechaVenSeguro = fechaVenSeguro;
        this.valido = valido;
        this.foto = foto;
        this.correoTaxista = correoTaxista;
        this.estado = estado;
        this.justificacion = justificacion;
    }

    public TaxiEntidadTemporal(TaxiEntidad taxiEntidad) {
        this.pkPlaca =  taxiEntidad.getPkPlaca();
        this.datafono = taxiEntidad.getDatafono();
        this.telefono = taxiEntidad.getTelefono();
        this.clase = taxiEntidad.getClase();
        this.tipo =  taxiEntidad.getTipo();
        this.fechaVenRtv =      taxiEntidad.getFechaVenRtv();
        this.fechaVenMarchamo = taxiEntidad.getFechaVenMarchamo();
        this.fechaVenSeguro = taxiEntidad.getFechaVenSeguro();
        this.valido = taxiEntidad.getValido();
        this.foto = taxiEntidad.getFoto();
        if( taxiEntidad.getDuennoTaxi() != null)
            this.correoTaxista = taxiEntidad.getDuennoTaxi().getPkCorreoUsuario();
        this.estado = taxiEntidad.isEstado();
        this.justificacion = taxiEntidad.getJustificacion();
    }

    public TaxiEntidad toTaxiEntidad(){
        TaxiEntidad taxiEntidad = new TaxiEntidad();
        taxiEntidad.setPkPlaca(this.pkPlaca);
        taxiEntidad.setDatafono(this.datafono);
        taxiEntidad.setTelefono(this.telefono);
        taxiEntidad.setClase(this.clase);
        taxiEntidad.setTipo(this.tipo);
        // taxiEntidad.setDuennoTaxi();
        taxiEntidad.setFoto(this.foto);
        taxiEntidad.setValido(this.valido);
        taxiEntidad.setFechaVenRtv(this.fechaVenRtv);
        taxiEntidad.setFechaVenMarchamo(this.fechaVenMarchamo);
        taxiEntidad.setFechaVenSeguro(this.fechaVenSeguro);
        taxiEntidad.setEstado(this.estado);
        taxiEntidad.setJustificacion(this.justificacion);
        // taxiEntidad.setTaxistaActual();
        // taxiEntidad.viajesByPkPlaca = viajesByPkPlaca;
        // taxiEntidad.taxistasQueMeConducen = taxistasQueMeConducen;

        return taxiEntidad;
    }

    public List<TaxiEntidadTemporal> toListTaxiTemporal(List<TaxiEntidad> taxiEntidades) {
        List<TaxiEntidadTemporal> taxiEntidadTemporales = new ArrayList<>();
        for (TaxiEntidad taxiEntidad: taxiEntidades) {
            taxiEntidadTemporales.add(new TaxiEntidadTemporal(taxiEntidad));

        }
        return taxiEntidadTemporales;
    }
    public String getPkPlaca() {
        return pkPlaca;
    }

    public void setPkPlaca(String pkPlaca) {
        this.pkPlaca = pkPlaca;
    }

    public Boolean getDatafono() {
        return datafono;
    }

    public void setDatafono(Boolean datafono) {
        this.datafono = datafono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getFechaVenRtv() {
        return fechaVenRtv;
    }

    public void setFechaVenRtv(Timestamp fechaVenRtv) {
        this.fechaVenRtv = fechaVenRtv;
    }

    public Timestamp getFechaVenMarchamo() {
        return fechaVenMarchamo;
    }

    public void setFechaVenMarchamo(Timestamp fechaVenMarchamo) {
        this.fechaVenMarchamo = fechaVenMarchamo;
    }

    public Timestamp getFechaVenSeguro() {
        return fechaVenSeguro;
    }

    public void setFechaVenSeguro(Timestamp fechaVenSeguro) {
        this.fechaVenSeguro = fechaVenSeguro;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCorreoTaxista() {
        return correoTaxista;
    }

    public void setCorreoTaxista(String correoTaxista) {
        this.correoTaxista = correoTaxista;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}
