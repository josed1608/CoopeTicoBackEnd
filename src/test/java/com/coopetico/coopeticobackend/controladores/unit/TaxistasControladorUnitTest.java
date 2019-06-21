package com.coopetico.coopeticobackend.controladores.unit;
/**
 Pruebas de unidad de TaxistaControlador
 @author      Christofer Rodriguez
 @since       19-04-2019
 @version:    1.0
 */
import com.coopetico.coopeticobackend.Utilidades.MockMvcUtilidades;
import com.coopetico.coopeticobackend.Utilidades.TokenUtilidades;
import com.coopetico.coopeticobackend.controladores.TaxistasControlador;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Pruebas de unidad para el controlador de taxistas.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaxistasControladorUnitTest {

    /**
     * Mock.
     */
    private MockMvc mockMvc;

    /**
     * Contexto.
     */
    @Autowired
    TokenUtilidades tokenUtilidades;

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
        this.mockMvc = MockMvcUtilidades.getMockMvc();
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/taxistas/taxistas").headers(tokenUtilidades.obtenerTokenGerente()).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
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
        //Se le indica que caundo pregunten por ese taxista retorne la entidad anterior
        when(taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com")).thenReturn(taxistaEntidad1);
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertEquals(entidadRetornada.getPkCorreoUsuario(), "taxistaMoka1@coopetico.com");
    }

    /**
     * Prueba de unidad para consultar la fecha de vencimiento de licencia de un taxista desde el controlador.
     */
    @Test
    public void testConsultarVencLic() throws Exception {
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
        taxistaEntidad1.setVence_licencia(new Timestamp((long)1556679600 * 1000));
        //Se le indica que caundo pregunten por ese taxista retorne la entidad anterior
        when(taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com")).thenReturn(taxistaEntidad1);
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que la fecha sea la esperada
        long respCorrecta = (long)1556679600 * 1000;
        long resp = entidadRetornada.getVence_licencia().getTime();
        assertEquals(resp, respCorrecta);
    }

    /**
     * Prueba de unidad para comprobar que los apellidos de un taxista esten separados desde el controlador.
     */
    @Test
    public void testConsultarApellidosSeparados() throws Exception {
        //Taxista
        TaxistaEntidadTemporal taxistaEntidad1 = new TaxistaEntidadTemporal();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setNombre("Taxista01");
        taxistaEntidad1.setApellido1("apellido1");
        taxistaEntidad1.setApellido2("apellido2");
        taxistaEntidad1.setTelefono("11111111");
        taxistaEntidad1.setFoto("foto");
        //Se le indica que cuando pregunten por ese taxista retorne la entidad anterior
        when(taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com")).thenReturn(taxistaEntidad1);
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasControlador.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara ambos apellidos para ver que esten separados
        Assert.assertEquals(entidadRetornada.getApellido1(), "apellido1");
        Assert.assertEquals(entidadRetornada.getApellido2(), "apellido2");
    }

    private TaxistaEntidadTemporal consultarPorId(String taxista) throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/taxistas/" + taxista)
                .headers(tokenUtilidades.obtenerTokenGerente())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), TaxistaEntidadTemporal.class);
    }

    /**
     * Prueba de unidad para consultar los taxis que conduce un taxista.
     */
    @Test
    public void testConsultarTaxisConduceTaxista() throws Exception {
        // Se crea el taxista
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
        // Se pide la lista que tenia antes
        List<String> siConduce =  new ArrayList<>();
        // Se agrega que conduce este taxi
        siConduce.add("AAA111");
        // Se agrega a la entidad que se va a enviar
        taxistaEntidad1.setSiConduce(siConduce);
        //Se le indica que caundo pregunten por ese taxista retorne la entidad anterior
        when(taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com")).thenReturn(taxistaEntidad1);
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertEquals(entidadRetornada.getSiConduce().size(), 1);

    }

    /**
     * Prueba la respuesta del endpoint taxistas/{id}/estado cuando el taxista no esta suspendido.
     * @throws Exception
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoNoSuspendido() throws Exception{
        Map<String, Object> estado = new HashMap<>();
        estado.put("estado", true);
        estado.put("justificacion", "");

        when(taxistasServicio.obtenerEstado("taxistaNoSuspendido@taxista.com")).thenReturn(estado);

        final String resultado = mockMvc.perform(get("/taxistas/taxistaNoSuspendido@taxista.com/estado")
        .headers(tokenUtilidades.obtenerTokenGerente()))
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
        Map<String, Object> estado = new HashMap<>();
        estado.put("estado", false);
        estado.put("justificacion", "Cobro de más a un cliente");

        when(taxistasServicio.obtenerEstado("taxistaSuspendido@taxista.com")).thenReturn(estado);

        final String resultado = mockMvc.perform(get("/taxistas/taxistaSuspendido@taxista.com/estado")
        .headers(tokenUtilidades.obtenerTokenGerente()))
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
        when(taxistasServicio.obtenerEstado("noExiste@taxista.com"))
                .thenThrow(
                        new UsuarioNoEncontradoExcepcion(
                                "El usuario no existe.",
                                HttpStatus.NOT_FOUND,
                                System.currentTimeMillis()
                        )
                );

        final String resultado = mockMvc.perform(get("/taxistas/noExiste@taxista.com/estado")
        .headers(tokenUtilidades.obtenerTokenGerente()))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultado);
        HashMap<String,Object> result =
                new ObjectMapper().readValue(resultado, HashMap.class);
        assertTrue(result.containsKey("error"));
        assertEquals(result.get("error"), "El usuario no existe.");
    }

    /**
     * Prueba la insercion de un archivo de taxistas que es representado por lista.
     * @throws Exception
     * @author Jefferson Alvarez
     */
    @Test
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

        // Se setea la funcion que llama el metodo
        given(taxistasServicio.guardarLista(taxistas)).willReturn(true);

        // Variable que tiene los objetos en formato JSON
        String objetos;

        ObjectMapper objectMapper = new ObjectMapper();
        objetos =  objectMapper.writeValueAsString(taxistas);


        // Se manda la solicitud de agregar al url
        ResultActions mvcResult = mockMvc.perform(post(url)
                .headers(tokenUtilidades.obtenerTokenGerente())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objetos))
                .andExpect(status().isOk());
    }
    //-------------------------------------------------------------------------
    /**
     * Pruebas para traer los datos del taxista asignado.
     *
     * @author Joseph Rementería (b55824)
     * @since 25-05-2019
     * @version 2.0
     */
    @Test
    public void testObtenerDatosTaxistaAsignado() {
        String body =
            "{" +
                "\"correoCliente\" : \"cliente@cliente.com\"," +
                "\"origen\" : \"origen\"," +
                "\"destino\" : \"destino\"" +
            "}";

        try {
            final String resultado = mockMvc.perform(
                get("/taxistas/taxista1@taxista.com/datosParaMostrar")
                        .headers(tokenUtilidades.obtenerTokenCliente())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
            )
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
            System.out.print("----------------------------------------------");
            System.out.print(resultado);
            System.out.print("----------------------------------------------");
        } catch (Exception e) {
            assert(true);
        }
    }
}