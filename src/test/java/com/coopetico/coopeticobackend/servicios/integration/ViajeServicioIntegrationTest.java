package com.coopetico.coopeticobackend.servicios.integration;

import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
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
    //-------------------------------------------------------------------------
    /**
     * Prueba para el método guardar el monto del viaje
     *
     * @author Joseph Rementería (b55824)
     * @since 23-06-2019
     */
    @Test
    public void testGuardarMonto(){
        //---------------------------------------------------------------------
        // Datos del viaje a probar
        String placa = "AAA111";
        String placaInvalida = "NA";
        String fechaInicio = "2019-04-20 04:20:00";
        String costoValido = "3000";
        //---------------------------------------------------------------------
        // Creación de la entidad
        ViajeEntidadPK pkValida = new ViajeEntidadPK(placa, fechaInicio);
        ViajeEntidadPK pkInvalida = new ViajeEntidadPK(
            placaInvalida,
            fechaInicio
        );
        ViajeEntidad viajeNuevo = new ViajeEntidad();
        //---------------------------------------------------------------------
        // Inserción de datos NOT NULL
        viajeNuevo.setViajeEntidadPK(pkValida);
        viajeNuevo.setTaxistaByCorreoTaxi(
            this.taxistasServicio.consultarTaxistaPorId(
                "taxista1@taxista.com"
            )
        );
        viajeNuevo.setTaxiByPkPlacaTaxi(
                this.taxisServicio.consultarPorId(placa)
        );
        viajeNuevo.setOrigen("$origen");
        //---------------------------------------------------------------------
        // Se asegura de que este la tupla en la base
        try {
            this.viajesRepositorio.deleteById(pkValida);
        } catch (Exception e) { }
        this.viajesRepositorio.save(viajeNuevo);
        //---------------------------------------------------------------------
        // Se prueba que devuelva el código correspondiente para el caso en
        // el que no se encuentre el viaje
        final int CODIGO_VIAJE_NO_ENCONTRADO = -2;
        //---------------------------------------------------------------------
        // Mockeo del método
        Assert.assertEquals(
                viajeServicio.guardarMonto(pkInvalida, costoValido),
                CODIGO_VIAJE_NO_ENCONTRADO
        );
        //---------------------------------------------------------------------
        // Se prueba que no se den fallos en el método
        final int CODIGO_VIAJE_EXITO = 0;
        Assert.assertEquals(
                viajeServicio.guardarMonto(pkValida, costoValido),
                CODIGO_VIAJE_EXITO
        );
    }
    //-------------------------------------------------------------------------

    /**
     * Prueba para el método finalizar viaje
     *
     * @author Marco Venegas (B67697)
     * @since 22-06-2019
     */
    @Test
    @Transactional
    public void asignarEstrellas(){
        ViajeEntidadPK pk = new ViajeEntidadPK("AAA111", "2019-05-30 14:28:00");
        try{
            viajesRepositorio.deleteById(pk);
        }catch(Exception e){}
        finally{
            viajeServicio.crear(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), "cliente@cliente.com", "origen", "taxista1@taxista.com");
            viajeServicio.finalizar(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), "2019-05-30 15:28:00");
            viajeServicio.asignarEstrellas(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), 5);

            ViajeEntidad insertado = viajesRepositorio.encontrarViaje(pk.getPkPlacaTaxi(), pk.getPkFechaInicio());

            Assert.assertEquals(insertado.getEstrellas(), new Integer(5));
        }
    }
}
