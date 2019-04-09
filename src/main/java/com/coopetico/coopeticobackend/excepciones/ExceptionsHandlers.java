package com.coopetico.coopeticobackend.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler
    public ResponseEntity<ExcepcionMensaje> handleException(ExcepcionGeneral e) {
        return new ResponseEntity<>(e.getExcepcionMensaje(), e.getExcepcionMensaje().getEstado());
    }

    @ExceptionHandler
    public ResponseEntity<ExcepcionMensaje> handleException(UsernameNotFoundException e) {
        ExcepcionMensaje mensaje = new ExcepcionMensaje(HttpStatus.NOT_FOUND, e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExcepcionMensaje> handleException(BadCredentialsException e) {
        ExcepcionMensaje mensaje = new ExcepcionMensaje(HttpStatus.UNAUTHORIZED, "Correo o contraseña inválida", System.currentTimeMillis());
        return new ResponseEntity<>(mensaje, HttpStatus.UNAUTHORIZED);
    }
}
