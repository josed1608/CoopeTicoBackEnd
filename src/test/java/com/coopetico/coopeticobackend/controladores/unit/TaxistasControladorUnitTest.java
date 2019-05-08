package com.coopetico.coopeticobackend.controladores.unit;
/**
 Pruebas de unidad de TaxistaControlador
 @author      Christofer Rodriguez
 @since       19-04-2019
 @version:    1.0
 */
import com.coopetico.coopeticobackend.controladores.TaxistasControlador;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Pruebas de unidad para el controlador de taxistas.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaxistasControladorUnitTest {

    /**
     * Mock.
     */
    private MockMvc mockMvc;

    /**
     * Contexto.
     */
    @Autowired
    protected WebApplicationContext wac;

    /**
     * Controlador de taxistas.
     */
    @Autowired
    TaxistasControlador taxistasControlador;

    /**
     * Servicio de taxistas.
     */
    @MockBean
    TaxistasServicioImpl taxistasServicio;

    /**
     * Inicializador del mock.
     */
    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.taxistasControlador).build();
    }

    /**
     * Prueba de unidad del consultar de todos los taxistas.
     */
    @Test
    public void testConsultar() throws Exception {
        //Taxista1
        TaxistaEntidadTemporal taxistaEntidad1 = new TaxistaEntidadTemporal();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setApellido1("Apellido1");
        taxistaEntidad1.setApellido2("Apellido2");
        taxistaEntidad1.setFoto("foto");
        taxistaEntidad1.setNombre("Taxista1");
        taxistaEntidad1.setTelefono("22333322");
        //Taxista2
        TaxistaEntidadTemporal taxistaEntidad2 = new TaxistaEntidadTemporal();
        taxistaEntidad2.setPkCorreoUsuario("taxistaMoka2@coopetico.com");
        taxistaEntidad2.setFaltas("0");
        taxistaEntidad2.setEstado(true);
        taxistaEntidad2.setHojaDelincuencia(true);
        taxistaEntidad2.setEstrellas(5);
        taxistaEntidad2.setApellido1("Apellido1");
        taxistaEntidad2.setApellido2("Apellido2");
        taxistaEntidad2.setFoto("foto");
        taxistaEntidad2.setNombre("Taxista2");
        taxistaEntidad2.setTelefono("22333322");
        //Txistas
        List<TaxistaEntidadTemporal> entidades = Arrays.asList(taxistaEntidad1,taxistaEntidad2);
        // Se le indica al servicio que devuelta la lista de taxistas cuando consulten por ella
        given(this.taxistasServicio.consultar()).willReturn(entidades);
        //Perdir los datos al controlador
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/taxistas/taxistas").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        //Verificar que respondio
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //Comparar el resultado con lo esperado
        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TaxistaEntidadTemporal [] taxista = objectMapper.readValue(contenido, TaxistaEntidadTemporal[].class);
        assertEquals(taxista.length,2);
    }

    /**
     * Prueba de unidad del consultar un solo taxista.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        //Taxista
        TaxistaEntidadTemporal taxistaEntidad1 = new TaxistaEntidadTemporal();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setNombre("Taxista01");
        taxistaEntidad1.setApellido1("apellido01");
        taxistaEntidad1.setApellido2("apellido02");
        taxistaEntidad1.setTelefono("11111111");
        taxistaEntidad1.setFoto("foto");
        //taxistaEntidad1.setPkPlaca("AAA111");
        //Se le indica que caundo pregunten por ese taxista retorne la entidad anterior
        when(taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com")).thenReturn(taxistaEntidad1);
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertTrue(entidadRetornada.getPkCorreoUsuario().equals("taxistaMoka1@coopetico.com"));
    }

    /**
     * Prueba la respuesta del endpoint taxistas/{id}/estado cuando el taxista no esta suspendido.
     * @throws Exception
     */
    @Test
    public void testObtenerEstadoNoSuspendido() throws Exception{
        Map<String, Object> estado = new HashMap<>();
        estado.put("estado", true);
        estado.put("justificacion", "");

        when(taxistasServicio.obtenerEstado("taxistaNoSuspendido@taxista.com")).thenReturn(estado);

        final String resultado = mockMvc.perform(get("/taxistas/taxistaNoSuspendido@taxista.com/estado"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HashMap<String,Object> result =
                new ObjectMapper().readValue(resultado, HashMap.class);
        assertTrue(result.containsKey("estado"));
        assertTrue(result.containsKey("justificacion"));
        assertTrue(result.get("estado").equals(true));
        assertTrue(result.get("justificacion").equals(""));
    }

    /**
     * Prueba la respuesta del endpoint taxistas/{id}/estado cuando el taxista esta suspendido.
     * @throws Exception
     */
    @Test
    public void testObtenerEstadoSuspendido() throws Exception{
        Map<String, Object> estado = new HashMap<>();
        estado.put("estado", false);
        estado.put("justificacion", "Cobro de más a un cliente");

        when(taxistasServicio.obtenerEstado("taxistaSuspendido@taxista.com")).thenReturn(estado);

        final String resultado = mockMvc.perform(get("/taxistas/taxistaSuspendido@taxista.com/estado"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HashMap<String,Object> result =
                new ObjectMapper().readValue(resultado, HashMap.class);
        assertTrue(result.containsKey("estado"));
        assertTrue(result.containsKey("justificacion"));
        assertTrue(result.get("estado").equals(false));
        assertTrue(result.get("justificacion").equals("Cobro de más a un cliente"));
    }

    /**
     * Prueba la respuesta del endpoint taxistas/{id}/estado cuando el taxista no existe.
     * @throws Exception
     */
    @Test
    public void testObtenerEstadoCorreoNoExistente() throws Exception{
        when(taxistasServicio.obtenerEstado("noExiste@taxista.com"))
                .thenThrow(
                        new UsuarioNoEncontradoExcepcion(
                                "El usuario no existe.",
                                HttpStatus.NOT_FOUND,
                                System.currentTimeMillis()
                        )
                );

        final String resultado = mockMvc.perform(get("/taxistas/noExiste@taxista.com/estado"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultado);
        HashMap<String,Object> result =
                new ObjectMapper().readValue(resultado, HashMap.class);
        assertTrue(result.containsKey("error"));
        assertEquals(result.get("error"), "El usuario no existe.");
    }
}