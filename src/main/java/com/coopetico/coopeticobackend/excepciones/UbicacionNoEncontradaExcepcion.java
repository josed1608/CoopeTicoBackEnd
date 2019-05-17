package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class UbicacionNoEncontradaExcepcion extends ExcepcionGeneral {
    public UbicacionNoEncontradaExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}