package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class InvalidJwtAuthenticationException extends ExcepcionGeneral {
    public InvalidJwtAuthenticationException(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}