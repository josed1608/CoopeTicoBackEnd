package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class GrupoNoExisteExcepcion extends ExcepcionGeneral {
    public GrupoNoExisteExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}
