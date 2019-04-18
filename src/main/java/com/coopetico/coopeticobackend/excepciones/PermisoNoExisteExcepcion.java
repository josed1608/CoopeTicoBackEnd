package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class PermisoNoExisteExcepcion extends ExcepcionGeneral {
    public PermisoNoExisteExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}