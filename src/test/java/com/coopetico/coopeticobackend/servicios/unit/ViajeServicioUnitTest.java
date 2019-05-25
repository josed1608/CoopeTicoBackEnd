//-----------------------------------------------------------------------------
// Package
package com.coopetico.coopeticobackend.servicios.unit;
//-----------------------------------------------------------------------------
// Imports
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;
//-----------------------------------------------------------------------------
/**
 * Clase para pruebas del servicio viajes.
 *
 * @author Joseph Rementería (b55824)
 * @since 25-05-2019\
 * @version 2.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ViajeServicioUnitTest {
    //-------------------------------------------------------------------------
    // Variables globales
    @Autowired
    ViajesServicio viajeServicio;
    //-------------------------------------------------------------------------
    // Pruebas
    /**
     * Prueba para el método crear viaje
     *
     * @author Joseph Rementería (b55824)
     * @since 25-05-2019
     */
    @Test
    public void testCrearViaje(){
        // Crear usuario
        viajeServicio.crear(
            "AAA111",
            new Timestamp(System.currentTimeMillis()),
            "correo@cliente.com",
            "origen",
            "taxista1@taxista.com"
        );
    }
    //-------------------------------------------------------------------------
}
