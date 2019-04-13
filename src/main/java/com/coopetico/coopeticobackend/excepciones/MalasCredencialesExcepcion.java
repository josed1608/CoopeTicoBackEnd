package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class MalasCredencialesExcepcion extends ExcepcionGeneral{
    public MalasCredencialesExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}
