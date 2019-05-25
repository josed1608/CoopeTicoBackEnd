package com.coopetico.coopeticobackend.controladores.integration;

/**
 Pruebas de integració del UbicacionTaxistasControlador
 @author      Marco Venegas
 @since       21-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.UbicacionTaxistasControlador;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.google.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UbicacionTaxistasControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    UbicacionTaxistasControlador ubicacionTaxistasControlador;

    @Autowired
    UbicacionTaxistasServicio ubicacionTaxistasServicio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.ubicacionTaxistasControlador).build();// Standalone context
    }

    /**
     * Prueba el endpoint de actualizar la ubicación y el estado de disponibilidad.
     * @throws Exception
     */
    @Test
    public void testActualizarUbicacionDisponible() throws Exception {
        mockMvc.perform(post("/ubicaciones/actualizar/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista1@taxista.com\"," +
                        "\"latitud\": 0.0," +
                        "\"longitud\": 0.0," +
                        "\"disponible\": true" +
                        "}"))
                //Assert
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint de actualizar la ubicación.
     * @throws Exception
     */
    @Test
    public void testActualizarUbicacion() throws Exception {
        mockMvc.perform(post("/ubicaciones/actualizar/ubicacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista1@taxista.com\"," +
                        "\"latitud\": 0.0," +
                        "\"longitud\": 0.0" +
                        "}"))
                //Assert
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint de actualizar el estado de disponibilidad.
     * @throws Exception
     */
    @Test
    public void testActualizarDisponible() throws Exception {
        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista1@taxista.com", new LatLng(0.0, 0.0), true);

        mockMvc.perform(post("/ubicaciones/actualizar/ubicacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista1@taxista.com\"," +
                        "\"disponible\": false" +
                        "}"))
                //Assert
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint de consultar la información de un taxista de la estructura.
     * @throws Exception
     */
    @Test
    public void testConsultarUbicacionDisponible() throws Exception {
        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista("taxista1@taxista.com", new LatLng(0.0, 0.0), true);

        mockMvc.perform(get("/ubicaciones/consultar/taxista1@taxista.com/")
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); //TODO Arreglar este test en algun momento
    }

    /**
     * Prueba el endpoint de eliminar un taxista de la estructura.
     * @throws Exception
     */
    @Test
    public void testEliminar() throws Exception {
        mockMvc.perform(delete("/ubicaciones/eliminar/taxista1@taxista.com/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
