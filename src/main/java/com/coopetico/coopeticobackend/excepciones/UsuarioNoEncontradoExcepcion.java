package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class UsuarioNoEncontradoExcepcion extends ExcepcionGeneral{
    public UsuarioNoEncontradoExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}