package com.coopetico.coopeticobackend.servicios;

// TODO Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.01
//Interfaz del Servicio de Permiso-Grupo.

import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;

public interface TokensRecuperacionContrasenaServicio {
    TokenRecuperacionContrasenaEntidad getToken(String correo);
    String insertarToken(String correo);
}
