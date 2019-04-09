package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class CorreoTomadoExcepcion extends ExcepcionGeneral{
    public CorreoTomadoExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}
