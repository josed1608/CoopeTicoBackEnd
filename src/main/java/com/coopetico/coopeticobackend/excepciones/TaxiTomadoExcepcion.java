package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class TaxiTomadoExcepcion extends ExcepcionGeneral{
    public TaxiTomadoExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}
