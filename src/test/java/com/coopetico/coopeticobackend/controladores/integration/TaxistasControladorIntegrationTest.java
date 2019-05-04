package com.coopetico.coopeticobackend.controladores.integration;

/**
 Pruebas de integracion del controlador de taxistas
 @author      Christofer Rodriguez
 @since       20-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.TaxistasControlador;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Pruebas de integracion para el controlador de taxistas.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaxistasControladorIntegrationTest {

    /**
     * Mock para llamar el controlador.
     */
    private MockMvc mockMvc;

    /**
     * Controlador del taxista.
     */
    @Autowired
    TaxistasControlador taxistasControlador;

    /**
     * Inicializador del mock para hacer las consultas.
     */
    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.taxistasControlador).build();
    }

    /**
     * Prueba de integracion para consultar los taxistas desde el controlador.
     */
    @Test
    @Transactional
    public void testConsultar() throws Exception {
        //Se hace la consulta al controlador
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/taxistas/taxistas").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        //Verificar que respondio
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //Comparar el resultado con lo esperado
        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TaxistaEntidadTemporal[] taxista = objectMapper.readValue(contenido, TaxistaEntidadTemporal[].class);
        assertEquals(taxista.length,2);
    }

    /**
     * Prueba de integracion para consultar un taxista desde el controlador.
     */
    @Test
    @Transactional
    public void testConsultarPorId() throws Exception {
        // Se hace la consulta al controlador
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        Assert.assertTrue(entidadRetornada.getPkCorreoUsuario().equals("taxista1@taxista.com"));
    }
}