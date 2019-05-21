package com.coopetico.coopeticobackend.controladores.integration;

import com.coopetico.coopeticobackend.controladores.AuthControlador;
import com.coopetico.coopeticobackend.controladores.UbicacionTaxistasControlador;
import com.coopetico.coopeticobackend.controladores.ViajeControlador;
import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeComenzandoEntidad;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.web.FilterChainProxy;
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

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\": \"cliente@cliente.com\"," +
                        "\"password\": \"contrasenna\"" +
                        "}"))
                .andReturn();
        String token = result.getResponse().getContentAsString();
        authHeaderCliente = new HttpHeaders();
        authHeaderCliente.add("Authorization", "Bearer " + token);

        // Obtener token de taxista
        result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\": \"taxista1@taxista.com\"," +
                        "\"password\": \"contrasenna\"" +
                        "}"))
                .andReturn();
        token = result.getResponse().getContentAsString();
        authHeaderTaxista = new HttpHeaders();
        authHeaderTaxista.add("Authorization", "Bearer " + token);

        // Obtener token de taxista2
        result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\": \"taxista2@taxista.com\"," +
                        "\"password\": \"contrasenna\"" +
                        "}"))
                .andReturn();
        token = result.getResponse().getContentAsString();
        authHeaderTaxista2 = new HttpHeaders();
        authHeaderTaxista2.add("Authorization", "Bearer " + token);

        // Cargar datos para test de la estructura
        mockMvc.perform(post("/ubicaciones/cargar-datos-test")).andReturn();
    }

    @Test
    @Transactional
    public void solicitarViaje() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(authHeaderTaxista), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe("/user/queue/recibir-viaje", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerViajeSolicitado());

        mockMvc.perform(get("/viajes/solicitar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                    "\t\"correoCliente\": \"cliente@cliente.com\",\n" +
                                    "\t\"origen\": \"9.963111,-84.054929\",\n" +
                                    "\t\"destino\": \"9.963111,-84.054929\",\n" +
                                    "\t\"tipo\": \"sedan\",\n" +
                                    "\t\"datafono\": true\n" +
                                "}"))
                .andExpect(content().string("Se le avisó al primer taxista taxista1@taxista.com"));

        ViajeComenzandoEntidad notificacionTaxista = completableFutureViajeComenzado.get(10, SECONDS);
        assertEquals("cliente@cliente.com", notificacionTaxista.getCorreoCliente());
    }

    @Test
    @Transactional
    public void aceptarViaje() throws Exception {
        WebSocketStompClient stompClientCliente = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClientCliente.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSessionCliente = stompClientCliente.connect(URL, new WebSocketHttpHeaders(authHeaderCliente), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSessionCliente.subscribe("/user/queue/esperar-taxista", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerTaxistaAsignado());

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

        DatosTaxistaAsigadoEntidad notificacionTaxista = completableFutureTaxistaAsignado.get(10, SECONDS);
        assertEquals("taxista1@taxista.com", notificacionTaxista.getCorreoTaxista());
    }

    @Test
    @Transactional
    public void rechazarViaje() throws Exception {
        WebSocketStompClient stompClientTaxista2 = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClientTaxista2.setMessageConverter(new MappingJackson2MessageConverter());
        StompSession stompSessionTaxista2 = stompClientTaxista2.connect(URL, new WebSocketHttpHeaders(authHeaderTaxista2), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
        stompSessionTaxista2.subscribe("/user/queue/recibir-viaje", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerViajeSolicitado());

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

        ViajeComenzandoEntidad viajeTaxista2 = completableFutureViajeComenzado.get(10, SECONDS);
        assertEquals("cliente@cliente.com", viajeTaxista2.getCorreoCliente());
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
}
