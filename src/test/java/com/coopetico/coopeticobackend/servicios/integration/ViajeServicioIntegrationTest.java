package com.coopetico.coopeticobackend.servicios.integration;

import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;
import com.coopetico.coopeticobackend.servicios.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ViajeServicioIntegrationTest {
    @Autowired
    ViajesServicio viajeServicio;

    @Autowired
    ViajesRepositorio viajesRepositorio;

    @Autowired
    ClienteServicio clienteServicio;
    @Autowired
    TaxisServicio taxisServicio;
    @Autowired
    TaxistasServicioImpl taxistasServicio;
    @Autowired
    OperadoresServicio operadoresServicio;


    /**
     * Prueba para el método finalizar viaje
     *
     * @author Marco Venegas (B67697)
     * @since 30-05-2019
     */
    @Test
    @Transactional
    public void finalizarViaje(){
        ViajeEntidadPK pk = new ViajeEntidadPK("AAA111", "2019-05-30 14:28:00");
        try{
            viajesRepositorio.deleteById(pk);
        }catch(Exception e){}
        finally{
            viajeServicio.crear(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), "cliente@cliente.com", "origen", "taxista1@taxista.com");
            viajeServicio.finalizar(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), "2019-05-30 15:28:00");

            ViajeEntidad insertado = viajesRepositorio.encontrarViaje(pk.getPkPlacaTaxi(), pk.getPkFechaInicio());

            Assert.assertEquals(insertado.getFechaFin(), "2019-05-30 15:28:00");
        }
    }
}
