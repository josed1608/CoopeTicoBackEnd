package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.UbicacionTaxiControlador;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;
import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UbicacionTaxiControladorUnitTest {


    @Autowired
    protected WebApplicationContext wac;

    // Mock del mvc
    private MockMvc mockMvc;

    // Beans de las inyecciones de dependencias
    @Autowired
    UbicacionTaxiControlador ubicacionTaxiControlador;

    // Mock del servicio de localizacion de los taxistas
    @MockBean
    UbicacionTaxistasServicio ubicacionTaxistasServicio;


    // Mock del servicio de localizacion de los taxis
    @MockBean
    TaxisServicio taxisServicio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(ubicacionTaxiControlador).build();
    }

    /**
     *  Metodo para testear el metodo de obtener taxi
     *  de manera que retorne el taxi correcto
     */
    @Test
    public void testObtenerTaxi(){
        /*
        String placaConsulta = "AAA112";
        TaxiEntidad taxiEntidad = ubicacionTaxiControlador.obtenerTaxi(getListaTaxisEntidad(), placaConsulta);
        assertNotNull(taxiEntidad);
        assertEquals( taxiEntidad.getPkPlaca(), placaConsulta );
        assertEquals( taxiEntidad.getTelefono(), "33334444" );

         */
    }

    /**
     * Metodo para testear el metodo de asociar taxis del controlador
     * permite ver si la asociacion de la estructura con las ubicaciones es correcta
     */
    @Test
    public void testAsociarTaxis(){
        /*
        List<TaxiTemporal> taxis = ubicacionTaxiControlador.asociarTaxis(getEstructuraTaxis(), getListaTaxisEntidad());
        assertNotNull(taxis);
        assertEquals(taxis.size(), 3);
        if (taxis.get(0).getPlaca().equals("AAA112")){
            assertEquals(taxis.get(0).getClase(), "B");
            assertFalse(taxis.get(0).isDatafonoMastercard());
        }


         */
    }

    /**
     * Metodo para testear el getTaxis del controlador
     * permite ver que los datos se están retornando en el orden correcto
     */
    @Test
    public void testGetTaxis() throws Exception {
        String url = "/ubicacion/taxis";

        // Estructuras de retorno para los mocks
        HashMap<String, Object[]> estructura = getEstructuraTaxis();
        List<TaxiEntidad> listaTaxi = getListaTaxisEntidad();

        // Se dice cual es el comportamiento que debería hacer cada servicio, dependiendo del
        // metodo que se llama
        given(ubicacionTaxistasServicio.getUbicaciones()).willReturn(estructura);
        given(taxisServicio.consultar()).willReturn(listaTaxi);

        // Se obtiene listas de taxis
        List<TaxiTemporal> taxis = ubicacionTaxiControlador.getTaxis();
        assertEquals(taxis.size() , 3);


        // Se prueba el get de la lista de taxis
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // Se obtiene el JSON
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        // Se pasa a una lista de Taxi temporal
        TaxiTemporal[] listaGrupos = objectMapper.readValue(content, TaxiTemporal[].class);
        assertNotNull(listaGrupos);
        assertEquals(listaGrupos.length, 3);
    }



    /**
     * Metodo que devuelve una lista con tres taxis para realizar las pruebas
     * @return Listas con taxis
     */
    public static List<TaxiEntidad> getListaTaxisEntidad(){
        List<TaxiEntidad> taxis = new ArrayList<>();
        Date date = new Date();
        taxis.add(new TaxiEntidad("AAA111", true, "11112222", "A", "Auto", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()), null, null,"foto.jpg", true, true, "",null, null));
        taxis.add(new TaxiEntidad("AAA112", false, "33334444", "B", "Auto", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()), null, null,"foto1.jpg", true, true, "",null, null));
        taxis.add(new TaxiEntidad("AAA113", true, "55556666", "C", "Camion", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()), null, null,"foto2.jpg", true, true, "",null, null));
        taxis.add(new TaxiEntidad("AAA114", true, "55556666", "C", "Camion", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()), null, null,"foto2.jpg", true, true, "",null, null));
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
    public static HashMap<String, Object[]> getEstructuraTaxis(){
        HashMap<String, Object[]> hashMap = new HashMap<>();
        Object[] valores = new Object[2];
        valores[0] = new LatLng(22.2222, 33.3333);
        valores[1] = true;
        hashMap.put("AAA111", valores);
        valores[0] = new LatLng(44.4444, 55.5555);
        hashMap.put("AAA112", valores);
        valores[0] = new LatLng(66.6666, 77.7777);
        hashMap.put("AAA113", valores);
        return hashMap;
    }
}

