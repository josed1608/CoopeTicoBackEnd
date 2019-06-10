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
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
        MvcResult mvcResult = mockMvc.perform(get("/taxistas/taxistas").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        //Verificar que respondio
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //Comparar el resultado con lo esperado
        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TaxistaEntidadTemporal[] taxista = objectMapper.readValue(contenido, TaxistaEntidadTemporal[].class);
        assertEquals(taxista.length,4);
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
        Assert.assertEquals(entidadRetornada.getPkCorreoUsuario(), "taxista1@taxista.com");
    }

    /**
     * Prueba de integracion para consultar la fecha de vencimiento de licencia de un taxista desde el controlador.
     */
    @Test
    @Transactional
    public void testConsultarVencLic() throws Exception {
        // Se hace la consulta al controlador
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxista2@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que la fecha sea la esperada
        long respCorrecta1 = (long)1556679600 * 1000;
        long respCorrecta2 = (long)1556690400 * 1000;
        long fecha = entidadRetornada.getVence_licencia().getTime();
        boolean resp = false;
        if ( fecha == respCorrecta1 || fecha == respCorrecta2 ){
            resp = true;
        }
        Assert.assertTrue(resp);
    }

    /**
     * Prueba de integracion para comprobar que los apellidos de un taxista esten separados desde el controlador.
     */
    @Test
    @Transactional
    public void testConsultarApellidosSeparados() throws Exception {
        // Se hace la consulta al controlador
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara ambos apellidos para ver que esten separados
        Assert.assertEquals(entidadRetornada.getApellido1(), "apellido");
        Assert.assertEquals(entidadRetornada.getApellido2(), "apellido");
    }

    /**
     * Prueba de integracion para consultar los taxis que conduce un taxista.
     */
    @Test
    @Transactional
    public void testConsultarTaxisConduceTaxista() throws Exception {
        // Se hace la consulta al controlador
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        Assert.assertEquals(entidadRetornada.getSiConduce().size(), 1);
    }

    /**
     * Prueba de integracion para agregar los taxis que conduce un taxista.
     */
    @Test
    @Transactional
    public void testAgregarTaxisConduceTaxista() throws Exception {
        // Se hace la consulta al controlador
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        Assert.assertEquals(entidadRetornada.getSiConduce().size(), 1);
        // Se pide la lista que tenia antes
        List<String> siConduce =  entidadRetornada.getSiConduce();
        // Se agrega que conduce este taxi
        siConduce.add("BBB111");
        // Se agrega a la entidad que se va a enviar
        entidadRetornada.setSiConduce(siConduce);
        entidadRetornada.setValid(true);
        //Se envia a guardar la entidad
        taxistasControlador.modificar(entidadRetornada, entidadRetornada.getPkCorreoUsuario());
        //Se consulta nuevamente el taxista para ver que conduce los 2 taxis
        entidadRetornada = taxistasControlador.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        int cantidadConduce = entidadRetornada.getSiConduce().size();
        Assert.assertEquals( cantidadConduce, 2);
    }

    /**
     * Prueba la respuesta del endpoint taxistas/{id}/estado cuando el taxista no esta suspendido.
     * @throws Exception
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoNoSuspendido() throws Exception{
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
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoSuspendido() throws Exception{
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
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoCorreoNoExistente() throws Exception{
        final String resultado = mockMvc.perform(get("/taxistas/noExiste@taxista.com/estado"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        HashMap<String,Object> result =
                new ObjectMapper().readValue(resultado, HashMap.class);
        assertTrue(result.containsKey("error"));
        assertTrue(result.get("error").equals("El usuario no existe."));
    }

        /**
     * Prueba la insercion de un archivo de taxistas que es representado por lista.
     * @throws Exception
     * @author Jefferson Alvarez
     */
    /*@Test
    @Transactional
    public void testGuardarTaxisArchivo() throws Exception {
        String url = "/taxistas/";

        // Se crea el taxista 1
        TaxistaEntidadTemporal taxistaEntidad1 = new TaxistaEntidadTemporal();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setNombre("Taxista01");
        taxistaEntidad1.setApellido1("apellido01");
        taxistaEntidad1.setApellido2("apellido02");
        taxistaEntidad1.setTelefono("88228821");
        taxistaEntidad1.setFoto("foto");
        taxistaEntidad1.setValid(true);

        // Se crea el taxista 2
        TaxistaEntidadTemporal taxistaEntidad2 = new TaxistaEntidadTemporal();
        taxistaEntidad2.setPkCorreoUsuario("taxistaMoka2@coopetico.com");
        taxistaEntidad2.setFaltas("0");
        taxistaEntidad2.setEstado(true);
        taxistaEntidad2.setHojaDelincuencia(true);
        taxistaEntidad2.setEstrellas(5);
        taxistaEntidad2.setNombre("Taxista02");
        taxistaEntidad2.setApellido1("apellido02");
        taxistaEntidad2.setApellido2("apellido02");
        taxistaEntidad2.setTelefono("88228822");
        taxistaEntidad2.setFoto("foto");
        taxistaEntidad2.setValid(true);

        // Se crea el taxista 3
        TaxistaEntidadTemporal taxistaEntidad3 = new TaxistaEntidadTemporal();
        taxistaEntidad3.setPkCorreoUsuario("taxistaMoka3@coopetico.com");
        taxistaEntidad3.setFaltas("0");
        taxistaEntidad3.setEstado(true);
        taxistaEntidad3.setHojaDelincuencia(true);
        taxistaEntidad3.setEstrellas(5);
        taxistaEntidad3.setNombre("Taxista03");
        taxistaEntidad3.setApellido1("apellido03");
        taxistaEntidad3.setApellido2("apellido03");
        taxistaEntidad3.setTelefono("88228823");
        taxistaEntidad3.setFoto("foto");
        taxistaEntidad3.setValid(true);

        // Se crea la lista de taxistas que representa al archivo
        List<TaxistaEntidadTemporal> taxistas = Arrays.asList(taxistaEntidad1, taxistaEntidad2, taxistaEntidad3);


        // Variable que tiene los objetos en formato JSON
        String objetos;

        ObjectMapper objectMapper = new ObjectMapper();
        objetos =  objectMapper.writeValueAsString(taxistas);


        // Se manda la solicitud de agregar al url
        ResultActions mvcResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objetos))
                .andExpect(status().isOk());

        assertTrue(taxistasControlador.consultar().size() >= 3);
    }*/
}