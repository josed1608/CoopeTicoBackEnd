package com.coopetico.coopeticobackend.entidades;

import java.io.Serializable;

/**
 * Clase para representar el request que trae la contraseña y el nombre de usuario del cliente que se quiere loggear
 * @author      Jose David Vargas Artavia
 */
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {
    }
}
