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
import com.google.maps.model.LatLng;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.util.Pair;

import java.util.HashMap;

/**
 * Clase de pruebas del UbicacionTaxistasServicio
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UbicacionTaxistasServicioUnitTest {
    /**
     * Métodos:
     * void upsertUbicacionDisponibleTaxista(String taxistaId, LatLng ubicacion, Boolean disponible);
     * void upsertUbicacionTaxista(String taxistaId, LatLng ubicacion);
     * void updateDisponibleTaxista(String taxistaId, Boolean disponible) throws UbicacionNoEncontradaExcepcion;
     * void eliminarTaxista(String taxistaId);
     * Object[] consultarUbicacionDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;
     * Object[] consultarUbicacionPairDisponible(String taxistaId) throws UbicacionNoEncontradaExcepcion;
     * HashMap<String, Object[]> getUbicaciones();
     */

    /**
     * Prueba la inserción de un dato a la estructura.
     */
    @Test
    public void insertarDato(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        Assert.assertEquals(ubicacionesTest.size(), 0); //No se ha insertado nada aún.

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", new LatLng(0.0, 0.0), true);
        Assert.assertEquals(ubicacionesTest.size(), 1);
    }

    /**
     * Prueba la actualización de el valor correspondiente a una llave.
     */
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

    /**
     * Prueba la actualización del campo de ubicación.
     * Se asegura que solo este campo sea modificado.
     */
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

    /**
     * Prueba la actualización del estado de disponibilidad cuando el valor ya existe en la estructura.     *
     */
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

    /**
     * Prueba que se lance una excepcion cuando se intenta actualizar el estado de disponibilidad
     * de una llave que no existe.
     */
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

    /**
     * Prueba la eliminación de un campo de la estructura de datos.
     */
    @Test
    public void eliminar(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", new LatLng(0.0, 0.0), true);

        Assert.assertEquals(ubicacionesTest.size(), 1); //Se insertó

        ubicacionTaxistasServicio.eliminarTaxista("taxista@taxista.com");

        Assert.assertEquals(ubicacionesTest.size(), 0); //Se eliminó
    }

    /**
     * Prueba la consulta de una llave cuando devuelve el valor como un arreglo de Objetos.
     */
    @Test
    public void consultar(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        Object[] datos = {new LatLng(0.0, 0.0), true};

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", (LatLng)datos[0], (boolean)datos[1]);

        Assert.assertEquals(ubicacionesTest.size(), 1); //Se insertó

        Object[] resultado = ubicacionTaxistasServicio.consultarUbicacionDisponible("taxista@taxista.com");

        Assert.assertEquals(datos[0], resultado[0]);
        Assert.assertEquals(datos[1], resultado[1]);
    }

    /**
     * Prueba la consulta de una llave cuando devuelve el valor como un Pair.
     */
    @Test
    public void consultarPair(){
        HashMap<String, Object[]> ubicacionesTest = new HashMap<>();
        UbicacionTaxistasServicio ubicacionTaxistasServicio = new UbicacionTaxistasServicioImpl(ubicacionesTest);

        Pair<Double, Double> ubicacion = Pair.of(0.0, 0.0);

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista@taxista.com", new LatLng(ubicacion.getFirst(), ubicacion.getSecond()), true);

        Assert.assertEquals(ubicacionesTest.size(), 1); //Se insertó

        Object[] resultado = ubicacionTaxistasServicio.consultarUbicacionPairDisponible("taxista@taxista.com");

        Assert.assertEquals((Pair<Double, Double>)resultado[0], ubicacion);
    }
}

