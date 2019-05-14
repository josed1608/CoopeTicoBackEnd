package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.UbicacionTaxiControlador;
import com.coopetico.coopeticobackend.controladores.UbicacionTaxistasControlador;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UbicacionTaxiControladorUnitTest {

    @Autowired
    protected WebApplicationContext wac;


    @Autowired
    UbicacionTaxiControlador ubicacionTaxiControlador;

    @Autowired
    UbicacionTaxistasServicio ubicacionTaxistasServicio;

    /**
     *  Metodo para testear el metodo de obtener taxi
     */
    @Test
    public void testObtenerTaxi(){
        String placaConsulta = "AAA112";
        TaxiEntidad taxiEntidad = ubicacionTaxiControlador.obtenerTaxi(getListaTaxisEntidad(), placaConsulta);
        assertNotNull(taxiEntidad);
        assertEquals( taxiEntidad.getPkPlaca(), placaConsulta );
        assertEquals( taxiEntidad.getTelefono(), "33334444" );

    }

    /**
     * Metodo para testear el metodo de asociar taxis del controlador
     */
    @Test
    public void testAsociarTaxis(){
        List<TaxiTemporal> taxis = ubicacionTaxiControlador.asociarTaxis(getEstructuraTaxis(), getListaTaxisEntidad());
        assertNotNull(taxis);
        assertEquals(taxis.size(), 3);
        if (taxis.get(0).getPlaca().equals("AAA112")){
            assertEquals(taxis.get(0).getClase(), "B");
            assertFalse(taxis.get(0).isDatafonoMastercard());
        }

    }

    /**
     * Metodo para testear el getTaxis del controlador
     */
    @Test
    public void testGetTaxis() throws Exception {
        // TODO metodo pendiente, esperar estructura de Marco
        String url = "/ubicacion";
        List<TaxiTemporal> entidades = getListaTaxisTemporal();
        //given(ubicacionTaxistasServicio.getTaxis()).willReturn(entidades);
        List<TaxiTemporal> taxis = ubicacionTaxiControlador.getTaxis();
        assertEquals(taxis.size() , 3);
    }



    /**
     * Metodo que devuelve una lista con tres taxis para realizar las pruebas
     * @return Listas con taxis
     */
    public static List<TaxiEntidad> getListaTaxisEntidad(){
        List<TaxiEntidad> taxis = new ArrayList<>();
        Date date = new Date();
        taxis.add(new TaxiEntidad("AAA111", true, "11112222", "A", "Auto", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()),null,"foto.jpg", null));
        taxis.add(new TaxiEntidad("AAA112", false, "33334444", "B", "Auto", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()),null,"foto1.jpg", null));
        taxis.add(new TaxiEntidad("AAA113", true, "55556666", "C", "Camion", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()),null,"foto2.jpg", null));
        taxis.add(new TaxiEntidad("AAA114", true, "55556666", "C", "Camion", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()),null,"foto2.jpg", null));
        return taxis;
    }

    /**
     * Metodo que devuelve una lista con tres taxis para realizar las pruebas
     * @return Listas con taxis
     */
    public static List<TaxiTemporal> getListaTaxisTemporal(){
        List<TaxiTemporal> taxis = new ArrayList<>();
        Date date = new Date();
        taxis.add(new TaxiTemporal("AAA111", 22.33159, 105.63233, "A", true, true));
        taxis.add(new TaxiTemporal("AAA112", 7.92658, -12.05228, "B", true, false));
        taxis.add(new TaxiTemporal("AAA113", 48.75606, -118.85900, "C", false, true));
        taxis.add(new TaxiTemporal("AAA114", 48.75606, -118.85900, "C", false, true));
        return taxis;
    }


    /**
     * Metodo que devuelve una estructura donde está la ubicacion de los taxis.
     * @return HashMap con la ubicacion de los taxis
     */
    public static HashMap<String, Double> getEstructuraTaxis(){
        HashMap<String, Double> hashMap = new HashMap<>();
        hashMap.put("AAA111", 22.33159);
        hashMap.put("AAA112", 7.92658);
        hashMap.put("AAA113", 48.75606);
        return hashMap;
    }
}

