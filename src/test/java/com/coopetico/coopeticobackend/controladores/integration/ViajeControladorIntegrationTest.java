package com.coopetico.coopeticobackend.controladores.integration;

import com.coopetico.coopeticobackend.controladores.ViajeControlador;
import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.entidades.ViajeComenzandoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidadPK;
import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ViajeControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @LocalServerPort
    private int port;
    private String URL;
    private HttpHeaders authHeaderCliente;
    private HttpHeaders authHeaderTaxista;
    private HttpHeaders authHeaderTaxista2;

    private CompletableFuture<ViajeComenzandoEntidad> completableFutureViajeComenzado;
    private CompletableFuture<DatosTaxistaAsigadoEntidad> completableFutureTaxistaAsignado;
    private CompletableFuture<String> completableFutureStrings;

    // Beans de las inyecciones de dependencias
    @Autowired
    ViajeControlador viajesControlador;

    @Autowired
    ClienteServicio clienteServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ViajesServicio viajeServicio;

    @Autowired
    ViajesRepositorio viajesRepositorio;

    /**
     * Se guarda el url para conectarse al webscoket, se inicia sesión y se toma el token para poder autenticarse al usar el websocket
     */
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        // URL del websocket
        completableFutureViajeComenzado = new CompletableFuture<>();
        completableFutureTaxistaAsignado = new CompletableFuture<>();
        completableFutureStrings = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/ws";

        // Obtener token de cliente
        authHeaderCliente = obtenerToken("cliente@cliente.com", "contrasenna");
        authHeaderTaxista = obtenerToken("taxista1@taxista.com", "contrasenna");
        authHeaderTaxista2 = obtenerToken("taxista2@taxista.com", "contrasenna");

        // Cargar datos para test de la estructura
        mockMvc.perform(post("/ubicaciones/cargar-datos-test")).andReturn();
    }

    /**
     * Método para generar el token correspondiente al usuario y contraseña dados, devuelve el header de autenticación ya armado
     *
     * @param usuario correo del usuario
     * @param contrasenna contraseña
     * @return retorna el header para autorizarse
     */
    private HttpHeaders obtenerToken(String usuario, String contrasenna) throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\": \"" + usuario + "\"," +
                        "\"password\": \"" + contrasenna + "\"" +
                        "}"))
                .andReturn();
        String token = result.getResponse().getContentAsString();
        HttpHeaders headerAuthorization = new HttpHeaders();
        headerAuthorization.add("Authorization", "Bearer " + token);
        return headerAuthorization;
    }

    /**
     * Crea la conexión con el websocket utiliando el token suministrado
     *
     * @param headerAutorizacion header con el token de autorización
     * @return retorna la sesión de la conexión con el WS
     */
    private StompSession obtenerSesionWS(HttpHeaders headerAutorizacion) throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        return stompClient.connect(URL, new WebSocketHttpHeaders(headerAutorizacion), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

    }

    /**
     * Testea que se solicite un viaje. El viaje se pide en plaza Lincoln, que es donde está el taxista1
     */
    @Test
    @Transactional
    public void solicitarViaje() throws Exception {
        // Arrange: Que el taxista se suscriba a recibir viajes
        StompSession stompSession = obtenerSesionWS(authHeaderTaxista);
        stompSession.subscribe("/user/queue/recibir-viaje", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerViajeSolicitado());

        // Act: que el cliente solicite un viaje
        mockMvc.perform(post("/viajes/solicitar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                    "\t\"correoCliente\": \"cliente@cliente.com\",\n" +
                                    "\t\"origen\": \"9.963111,-84.054929\",\n" +
                                    "\t\"destino\": \"9.963111,-84.054929\",\n" +
                                    "\t\"tipo\": \"sedan\",\n" +
                                    "\t\"datafono\": true\n" +
                                "}"))
                // Assert: que se haya escogido un taxista
                .andExpect(content().string("Se le avisó al primer taxista taxista1@taxista.com"));

        // Assert: Que al taxista le haya llegado la notificación de viaje
        ViajeComenzandoEntidad notificacionTaxista = completableFutureViajeComenzado.get(10, SECONDS);
        assertEquals("cliente@cliente.com", notificacionTaxista.getCorreoCliente());
    }


    /**
     * Prueba que si un taxista rechaza un mensaje, que se le avise al siguiente taxista
     */
    @Test
    @Transactional
    public void rechazarViaje() throws Exception {
        // Arrange, que el taxista al que le va a caer de segundo el vijae se suscriba
        StompSession stompSessionTaxista2 = obtenerSesionWS(authHeaderTaxista2);
        stompSessionTaxista2.subscribe("/user/queue/recibir-viaje", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerViajeSolicitado());

        // Act, que el cliente pida el viaje
        mockMvc.perform(post("/viajes/aceptar-rechazar?respuesta=false")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"correoCliente\": \"cliente@cliente.com\",\n" +
                        "\t\"origen\": \"9.963111,-84.054929\",\n" +
                        "\t\"destino\": \"9.963111,-84.054929\",\n" +
                        "\t\"tipo\": \"sedan\",\n" +
                        "\t\"datafono\": true,\n" +
                        "\t\"taxistasQueRechazaron\": []" +
                        "}")
                .headers(authHeaderTaxista))
                .andExpect(status().isOk());

        // Assert: asegurarse que al segundo taxista le llegara el viaje
        ViajeComenzandoEntidad viajeTaxista2 = completableFutureViajeComenzado.get(10, SECONDS);
        assertEquals("cliente@cliente.com", viajeTaxista2.getCorreoCliente());
    }
    /**
     * Testea que el taxista acepte el viaje y se le avise al cliente
     */
    @Test
    @Transactional
    public void aceptarViaje() throws Exception {
        // Arrange: que el cliente se suscriba a esperar taxista
        StompSession stompSessionCliente = obtenerSesionWS(authHeaderCliente);
        stompSessionCliente.subscribe("/user/queue/esperar-taxista", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerTaxistaAsignado());
        Thread.sleep(1000);

        // Act: Que el taxista responda afirmativo al viaje
        mockMvc.perform(post("/viajes/aceptar-rechazar?respuesta=true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                            "\t\"correoCliente\": \"cliente@cliente.com\",\n" +
                            "\t\"origen\": \"9.963111,-84.054929\",\n" +
                            "\t\"destino\": \"9.963111,-84.054929\",\n" +
                            "\t\"tipo\": \"sedan\",\n" +
                            "\t\"datafono\": true,\n" +
                            "\t\"taxistasQueRechazaron\": []" +
                        "}")
                .headers(authHeaderTaxista))
            .andExpect(content().string("Viaje comienza"));

        // Assert: Que al cliente le haya llegado la info del taxista1
        DatosTaxistaAsigadoEntidad notificacionTaxista = completableFutureTaxistaAsignado.get(10, SECONDS);
        assertEquals("taxista1@taxista.com", notificacionTaxista.getCorreoTaxista());
    }

    /**
     * Crea la lista donde se guarda lo que recibe el socket
     * @return la lista
     */
    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    /**
     * Clase que implementa los métodos para manejar los mensajes que llegan cuando se suscribe a un endpoint
     */
    private class CreateStringStompFrameHandlerViajeSolicitado implements StompFrameHandler {
        //Devuelve el tipo de los mensajes
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ViajeComenzandoEntidad.class;
        }

        //Toma el mensaje y lo agrega al CompletableFuture para que el método del test lo vea
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFutureViajeComenzado.complete((ViajeComenzandoEntidad) o);
        }
    }

    /**
     * Clase que implementa los métodos para manejar los mensajes que llegan cuando se suscribe a un endpoint
     */
    private class CreateStringStompFrameHandlerTaxistaAsignado implements StompFrameHandler {
        //Devuelve el tipo de los mensajes
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return DatosTaxistaAsigadoEntidad.class;
        }

        //Toma el mensaje y lo agrega al CompletableFuture para que el método del test lo vea
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFutureTaxistaAsignado.complete((DatosTaxistaAsigadoEntidad) o);
        }
    }

    /**
     * Clase que implementa los métodos para manejar los mensajes que llegan cuando se suscribe a un endpoint
     */
    private class CreateStringStompFrameHandlerStrings implements StompFrameHandler {
        //Devuelve el tipo de los mensajes
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return String.class;
        }

        //Toma el mensaje y lo agrega al CompletableFuture para que el método del test lo vea
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFutureStrings.complete((String) o);
        }
    }

    /**
     * Test de obtener viajes
     */
    /*@Test
    public void testobtenerViajes() throws Exception {
        List<ViajeEntidadTemporal> viajesRetorno = viajesControlador.obtenerViajes();
        assertNotNull(viajesRetorno);
        Assert.assertEquals(viajesRetorno.size(), 2);
    }*/

    /**
     * Metodo para obtener un usuario para las pruebas
     * @return Retorna un objeto de tipo usuarioEntidad
     */
    public static UsuarioTemporal getUsuarioTemporal(){
        UsuarioTemporal usuarioTemporal = new UsuarioTemporal();
        usuarioTemporal.setCorreo("gerente11@gerente.com");
        usuarioTemporal.setNombre("Gerente");
        usuarioTemporal.setApellido1("Apellido1");
        usuarioTemporal.setApellido2("Apellido2");
        usuarioTemporal.setTelefono("11111111");
        usuarioTemporal.setContrasena("$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify");
        usuarioTemporal.setFoto("foto");
        usuarioTemporal.setIdGrupo("Cliente");
        return usuarioTemporal;
    }

    /**
     * Metodo para obtener un usuario para las pruebas
     * @return Retorna un objeto de tipo usuarioTemporal
     */
    public static UsuarioEntidad getUsuarioEntidad(){
        return getUsuarioTemporal().convertirAUsuarioEntidad();
    }

    /**
     * Prueba para el endpoint finalizar viaje
     *
     * @author Marco Venegas (B67697)
     * @since 30-05-2019
     */
    /*@Test
    public void finalizarViaje(){
        ViajeEntidadPK pk = new ViajeEntidadPK("AAA111", "2019-05-30 14:28:00");
        try{
            viajesRepositorio.deleteById(pk);
        }catch(Exception e){}
        finally{
            viajeServicio.crear(pk.getPkPlacaTaxi(), pk.getPkFechaInicio(), "cliente@cliente.com", "origen", "taxista1@taxista.com");

            try{
                mockMvc.perform(
                        put("/viajes/finalizar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{" +
                                                "\"placa\": \"AAA111\"," +
                                                "\"fechaInicio\": \"2019-05-30 14:28:00\"," +
                                                "\"fechaFin\": \"2019-05-30 15:30:00\"" +
                                                "}"
                                )
                )
                        .andExpect(status().isOk());
            }catch(Exception e){
                fail();
            }

            ViajeEntidad insertado = viajesRepositorio.encontrarViaje(pk.getPkPlacaTaxi(), pk.getPkFechaInicio());

            Assert.assertEquals(insertado.getFechaFin(), "2019-05-30 15:30:00");
        }
    }*/
    //-------------------------------------------------------------------------
    /**
     * Prueba para el endpoint guardar monto
     *
     * @author Joseph Rementería (b55824)
     * @since 23.-06-2019
     */
    @Test
    public void guardarMonto() {
        //---------------------------------------------------------------------
        /*when(
                viajeServicio.guardarMonto(
                        any(ViajeEntidadPK.class),
                        any(String.class)
                )
        ).thenReturn(0);*/
        //---------------------------------------------------------------------
        try{
            mockMvc.perform(
                put("/viajes/costoViaje/5000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{" +
                        "\"placa\": \"AAA111\"," +
                        "\"fechaInicio\": \"2019-05-29 15:48:00\"" +
                    "}"
                )
            )
            .andExpect(status().isOk());
        } catch (Exception e) {
            fail();
        }
        //---------------------------------------------------------------------
    }
    //-------------------------------------------------------------------------
}
