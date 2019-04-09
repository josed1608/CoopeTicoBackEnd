package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class ExcepcionGeneral extends RuntimeException {
    private ExcepcionMensaje excepcionMensaje;

    public ExcepcionGeneral(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje);
        this.excepcionMensaje = new ExcepcionMensaje(estado, mensaje, timestamp);
    }

    public ExcepcionMensaje getExcepcionMensaje() {
        return excepcionMensaje;
    }

    public void setExcepcionMensaje(ExcepcionMensaje excepcionMensaje) {
        this.excepcionMensaje = excepcionMensaje;
    }
}
