package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

/**
 * Clase de la que todas las excepciones custom van a heredar
 */
class ExcepcionGeneral extends RuntimeException {
    private ExcepcionMensaje excepcionMensaje;

    ExcepcionGeneral(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje);
        this.excepcionMensaje = new ExcepcionMensaje(estado, mensaje, timestamp);
    }

    ExcepcionMensaje getExcepcionMensaje() {
        return excepcionMensaje;
    }

    void setExcepcionMensaje(ExcepcionMensaje excepcionMensaje) {
        this.excepcionMensaje = excepcionMensaje;
    }
}
