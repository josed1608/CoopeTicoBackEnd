//-----------------------------------------------------------------------------
// Package
package com.coopetico.coopeticobackend.servicios.unit;
//-----------------------------------------------------------------------------
// Imports
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;
import com.coopetico.coopeticobackend.servicios.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @MockBean
    ViajesRepositorio viajesRepositorio;

    @MockBean
    ClienteServicio clienteServicio;
    @MockBean
    TaxisServicio taxisServicio;
    @MockBean
    TaxistasServicioImpl taxistasServicio;
    @MockBean
    OperadoresServicio operadoresServicio;
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
            //new Timestamp(System.currentTimeMillis()),
            "2019-05-11 20:08:27",
            "correo@cliente.com",
            "origen",
            "taxista1@taxista.com"
        );
    }
    //-------------------------------------------------------------------------


    @Test
    public void finalizarViaje(){
        String placa = "AAA111";
        String fechaInicio = "2019-04-20 04:20:00";
        String fechaFin = "2019-04-20 04:21:00";

        ViajeEntidadPK pk = new ViajeEntidadPK(placa, fechaInicio);
        ViajeEntidad viajeMock = new ViajeEntidad();
        viajeMock.setViajeEntidadPK(pk);
        viajeMock.setFechaFin("");

        //Si no hay viaje, devuelve -2
        ViajeEntidad viajeTest = null;
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, fechaFin), -2);

        //Si ya tiene fecha de finalización, devuelve -3
        viajeTest = viajeMock;
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, fechaFin), -3);

        //Si tiene el formato de fecha invalido, devuelve -4
        viajeTest.setFechaFin(null);
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, ""), -4);

        //Si se intenta finalizar con una fecha anterior a la de inicio, devuelve -5
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, "2019-04-20 04:19:00"), -5);

        //Si es exitoso, devuelve 0
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, fechaFin), -0);

        //Si no se puede guardar en la base, devuelve -6
        //viajeTest.setFechaFin(null);
        //when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenReturn(viajeTest);
        //when(viajesRepositorio.save(any(ViajeEntidad.class))).thenThrow();
        //Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, fechaFin), -6);

        //Si algo pasa en el try, devuelve -1
        when(viajesRepositorio.encontrarViaje(any(String.class), any(String.class))).thenThrow();
        Assert.assertEquals(viajeServicio.finalizar(placa, fechaInicio, fechaFin), -1);

    }
}
