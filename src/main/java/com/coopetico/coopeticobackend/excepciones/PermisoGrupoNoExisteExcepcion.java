package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;

public class PermisoGrupoNoExisteExcepcion extends ExcepcionGeneral {
    public PermisoGrupoNoExisteExcepcion(String mensaje, HttpStatus estado, long timestamp) {
        super(mensaje, estado, timestamp);
    }
}