package com.coopetico.coopeticobackend.Util;

import java.util.UUID;

/**
 * Clase con metodos que se puedan compartir en varias clases
 */
public class Utileria {
    /**
     * Metodo que genera una contrasena defecto segun un nombre y un apellido
     * @param nombre Nombre
     * @param apellido Apellido
     * @return Nueva contrasena
     */
    public static String generarContrasena(String nombre, String apellido){
        if( nombre.length() > 3 && apellido.length()> 3 )
            return nombre.substring(0,3).concat(apellido.substring(0,2).concat(UUID.randomUUID().toString().substring(0,5)));
        return UUID.randomUUID().toString();
    }
}
