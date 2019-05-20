package com.coopetico.coopeticobackend.servicios.unit;

/**
 Pruebas de unidad del UbicacionTaxistasServicio
 @author      Marco Venegas
 @since       20-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.excepciones.UbicacionNoEncontradaExcepcion;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicioImpl;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.google.maps.model.LatLng;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UbicacionTaxistasServicioUnitTest {
    /**
     * void upsertUbicacionDisponibleTaxista(String taxistaId, LatLng ubicacion, Boolean disponible);
     * void upsertUbicacionTaxista(String taxistaId, LatLng ubicacion);
     * void updateDisponibleTaxista(String taxistaId, Boolean disponible) throws UbicacionNoEncontradaExcepcion;
     * void eliminarTaxista(String taxistaId);
     * Object[] consultarUbicacionDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;
     * Object[] consultarUbicacionPairDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;
     * HashMap<String, Object[]> getUbicaciones();
     */

    @Test
    public void insertarDato(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", new LatLng(0.0, 0.0), true);
        Assert.assertEquals(ubicacionesTest.size(), 1);
    }

    @Test
    public void actualizarUbicacionDisponible(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        LatLng ubicacionVieja = new LatLng(0.0, 0.0);
        boolean disponibleViejo = true;

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", ubicacionVieja, disponibleViejo);

        LatLng ubicacionNueva = new LatLng(5.5, 5.5);
        boolean disponibleNuevo = false;

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", ubicacionNueva, disponibleNuevo);

        Assert.assertEquals(ubicacionesTest.size(), 1); //No se insertó otro dato
        Assert.assertNotEquals(ubicacionVieja, ubicacionesTest.get("taxista@taxista.com")[0]);
        Assert.assertNotEquals(ubicacionVieja, ubicacionesTest.get("taxista@taxista.com")[1]);
    }

    @Test
    public void actualizarUbicacion(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        LatLng ubicacionVieja = new LatLng(0.0, 0.0);
        boolean disponibleViejo = true;

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", ubicacionVieja, disponibleViejo);
        LatLng ubicacionNueva = new LatLng(5.5, 5.5);

        ubicacionTaxistasServicio.upsertUbicacionTaxista("taxista@taxista.com", ubicacionNueva);

        Assert.assertEquals(ubicacionesTest.size(), 1); //No se insertó otro dato
        Assert.assertNotEquals(ubicacionVieja, ubicacionesTest.get("taxista@taxista.com")[0]);
        Assert.assertEquals(disponibleViejo, ubicacionesTest.get("taxista@taxista.com")[1]); //Disponible no cambia
    }

    @Test
    public void actualizarDisponibleExiste(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        LatLng ubicacionVieja = new LatLng(0.0, 0.0);
        boolean disponibleViejo = true;

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", ubicacionVieja, disponibleViejo);

        boolean disponibleNuevo = false;

        ubicacionTaxistasServicio.updateDisponibleTaxista("taxista@taxista.com", disponibleNuevo);

        Assert.assertEquals(ubicacionesTest.size(), 1); //No se insertó otro dato
        Assert.assertEquals(ubicacionVieja, ubicacionesTest.get("taxista@taxista.com")[0]); //Ubicacion no cambia
        Assert.assertNotEquals(disponibleViejo, ubicacionesTest.get("taxista@taxista.com")[1]);
    }

    @Test
    public void actualizarDisponibleNoExiste(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        boolean disponibleNuevo = false;

        try{
            ubicacionTaxistasServicio.updateDisponibleTaxista("taxista@taxista.com", disponibleNuevo);
            Assert.fail(); //Debe tirar excepcion
        }catch (UbicacionNoEncontradaExcepcion e){
            Assert.assertEquals(ubicacionesTest.size(), 0); //No se insertó nada
        }
    }

    @Test
    public void eliminar(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", new LatLng(0.0, 0.0), true);

        Assert.assertEquals(ubicacionesTest.size(), 1); //Se insertó

        ubicacionTaxistasServicio.eliminarTaxista("taxista@taxista.com");

        Assert.assertEquals(ubicacionesTest.size(), 0); //Se eliminó
    }
}

