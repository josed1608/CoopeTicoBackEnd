package com.coopetico.coopeticobackend.entidades;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import java.util.ArrayList;
import java.util.List;

public class ViajeEntidadTemporal {
    private String placaTaxi;
    private String fechaFin;
    private String fechaInicio;
    private String costo;
    private float estrellas;
    private String origen;
    private String destino;
    private String agendaTelefono;
    private String agendaNombre;
    private String correoCliente;
    private String correoTaxista;
    private String correoOperador;

    public ViajeEntidadTemporal(){}

    public ViajeEntidadTemporal(ViajeEntidad viajeEntidad){
        this.placaTaxi = viajeEntidad.getViajeEntidadPK().getPkPlacaTaxi();
        this.fechaInicio = viajeEntidad.getViajeEntidadPK().getPkFechaInicio().toString().replace('T',' ').substring(0,16);
        this.fechaFin = viajeEntidad.getFechaFin().toString().replace('T',' ').substring(0,16);
        this.costo = viajeEntidad.getCosto();
        this.estrellas = viajeEntidad.getEstrellas();
        this.origen = viajeEntidad.getOrigen();
        this.destino = viajeEntidad.getDestino();
        this.agendaTelefono = viajeEntidad.getOrigen();
        this.agendaNombre = viajeEntidad.getAgendaNombre();
        this.correoCliente = viajeEntidad.getClienteByPkCorreoCliente().getPkCorreoUsuario();
        this.correoTaxista = viajeEntidad.getTaxistaByCorreoTaxi().getPkCorreoUsuario();
        this.correoOperador = viajeEntidad.getAgendaOperador().getPkCorreoUsuario();
    }

    public String getPlacaTaxi() {
        return placaTaxi;
    }

    public void setPlacaTaxi(String placaTaxi) {
        this.placaTaxi = placaTaxi;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAgendaTelefono() {
        return agendaTelefono;
    }

    public void setAgendaTelefono(String agendaTelefono) {
        this.agendaTelefono = agendaTelefono;
    }

    public String getAgendaNombre() {
        return agendaNombre;
    }

    public void setAgendaNombre(String agendaNombre) {
        this.agendaNombre = agendaNombre;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getCorreoTaxista() {
        return correoTaxista;
    }

    public void setCorreoTaxista(String correoTaxista) {
        this.correoTaxista = correoTaxista;
    }

    public String getCorreoOperador() {
        return correoOperador;
    }

    public void setCorreoOperador(String correoOperador) {
        this.correoOperador = correoOperador;
    }

    public List<ViajeEntidadTemporal> convertirListaViajes(List<ViajeEntidad> viajeEntidad){
        List<ViajeEntidadTemporal> viajeEntidadTemporal = new ArrayList<>();
        for (ViajeEntidad viaje : viajeEntidad) {
            viajeEntidadTemporal.add(new ViajeEntidadTemporal(viaje));
        }
        return viajeEntidadTemporal;
    }
}
