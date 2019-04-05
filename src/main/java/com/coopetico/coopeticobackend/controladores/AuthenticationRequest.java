package com.coopetico.coopeticobackend.controladores;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
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
