package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

/**
 * Excepción en caso de que el usuario que se quiere crear ya existe
 */
public class CorreoTomadoExcepcion extends ExcepcionGeneral{
    public CorreoTomadoExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}
