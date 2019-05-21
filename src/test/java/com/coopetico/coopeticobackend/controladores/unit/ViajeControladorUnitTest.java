//-----------------------------------------------------------------------------
// Package
package com.coopetico.coopeticobackend.controladores.unit;
//-----------------------------------------------------------------------------
// Imports
import com.coopetico.coopeticobackend.controladores.ViajeControlador;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Calendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.
    MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.
    MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.
    MockMvcBuilders.standaloneSetup;
//-----------------------------------------------------------------------------
/**
 * Pruebas unitarias para los métodos de la clase ViajeControlador
 *
 * @author Joseph Rementería (b55824)
 * @since 21-05-2019
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ViajeControladorUnitTest {
    //-------------------------------------------------------------------------
    // Variables globales
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    ViajeControlador viajeControlador;
    @MockBean
    ViajesServicio viajesServicio;
    @MockBean
    ClienteServicio clienteServicio;
    //-------------------------------------------------------------------------
    // "Constructores"
    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.viajeControlador).build();
    }
    //-------------------------------------------------------------------------
    // Pruebas

    /**
     * Método que prueba la creación del viaje en la base de datos.
     *
     * @author Joseph Rementería (b55824)
     * @since 21-05-2019
     *
     * @throws Exception cuando hay un error.
     */
    @Test
    public void testCrearUsuario() throws Exception {
        String time = LocalDate.now().toString();
        mockMvc.perform(
            post("/viajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{" +
                    "\"placa\": \"AAA111\"," +
                    "\"fechaInicio\": \"" + time + "\"," +
                    "\"correoCliente\": \"cliente@cliente.com\"," +
                    "\"origen\": \"punto_de_origen\"," +
                    "\"correoTaxista\": \"taxista1@taxista.com\"" +
                    "}"
                )
        )
            .andExpect(status().isOk());
    }
    //-------------------------------------------------------------------------
}
//-----------------------------------------------------------------------------