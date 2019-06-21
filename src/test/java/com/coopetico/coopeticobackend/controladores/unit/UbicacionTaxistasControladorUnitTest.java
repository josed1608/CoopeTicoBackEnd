package com.coopetico.coopeticobackend.controladores.unit;

/**
 Pruebas de unidad del UbicacionTaxistasControlador
 @author      Marco Venegas
 @since       21-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.Utilidades.MockMvcUtilidades;
import com.coopetico.coopeticobackend.Utilidades.TokenUtilidades;
import com.coopetico.coopeticobackend.controladores.UbicacionTaxistasControlador;
import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@EnableWebMvc
@RunWith(SpringRunner.class)
public class UbicacionTaxistasControladorUnitTest {

    private MockMvc mockMvc;

    @Autowired
    TokenUtilidades tokenUtilidades;
    @Autowired
    UbicacionTaxistasControlador ubicacionTaxistasControlador;

    @MockBean
    UbicacionTaxistasServicio ubicacionTaxistasServicio;
    @MockBean
    TaxistasServicioImpl taxistasServicio;

    @Before
    public void setup() {
        this.mockMvc = MockMvcUtilidades.getMockMvc();// Standalone context
        Map<String, Object> estadoTaxista = new HashMap<>();
        estadoTaxista.put("estado", true);
        estadoTaxista.put("justificacion", "");
        when(taxistasServicio.obtenerEstado(any(String.class))).thenReturn(estadoTaxista);
    }

    /**
     * Prueba el endpoint de actualizar la ubicación y el estado de disponibilidad.
     * @throws Exception
     */
    @Test
    public void testActualizarUbicacionDisponible() throws Exception {
        //Arrange
        TaxistaEntidad mockTaxista = new TaxistaEntidad();
        when(taxistasServicio.taxistaPorCorreo(any(String.class))).thenReturn(Optional.of(mockTaxista));

        //Act
        mockMvc.perform(post("/ubicaciones/actualizar/todo")
                .headers(tokenUtilidades.obtenerTokenTaxista(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista@taxista.com\"," +
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
        //Arrange
        TaxistaEntidad mockTaxista = new TaxistaEntidad();
        when(taxistasServicio.taxistaPorCorreo(any(String.class))).thenReturn(Optional.of(mockTaxista));

        //Act
        mockMvc.perform(post("/ubicaciones/actualizar/ubicacion")
                .headers(tokenUtilidades.obtenerTokenTaxista(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista@taxista.com\"," +
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
        //Arrange
        TaxistaEntidad mockTaxista = new TaxistaEntidad();
        when(taxistasServicio.taxistaPorCorreo(any(String.class))).thenReturn(Optional.of(mockTaxista));

        //Act
        mockMvc.perform(post("/ubicaciones/actualizar/ubicacion")
                .headers(tokenUtilidades.obtenerTokenTaxista(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"correoTaxista\": \"taxista@taxista.com\"," +
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
        Pair<Double, Double> pareja = Pair.of(0.0, 0.0);
        Object[] arreglo = new Object[2];
        arreglo[0] = pareja;
        arreglo[1] = true;
        //Arrange
        when(ubicacionTaxistasServicio.consultarUbicacionPairDisponible(any(String.class))).thenReturn(arreglo);
        //Act
        mockMvc.perform(get("/ubicaciones/consultar/taxista@taxista.com/")
                .headers(tokenUtilidades.obtenerTokenCliente())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint de eliminar un taxista de la estructura.
     * @throws Exception
     */
    @Test
    public void testEliminar() throws Exception {
        //Arrange
        TaxistaEntidad mockTaxista = new TaxistaEntidad();
        when(taxistasServicio.taxistaPorCorreo(any(String.class))).thenReturn(Optional.of(mockTaxista));
        //Assert
        mockMvc.perform(delete("/ubicaciones/eliminar/taxista@taxista.com/")
                .headers(tokenUtilidades.obtenerTokenGerente())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
