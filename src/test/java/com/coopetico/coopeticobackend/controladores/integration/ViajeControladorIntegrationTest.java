package com.coopetico.coopeticobackend.controladores.integration;

import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeComenzandoEntidad;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(get("/viajes/solicitar")
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
     * Testea que el taxista acepte el viaje y se le avise al cliente
     */
    @Test
    @Transactional
    public void aceptarViaje() throws Exception {
        // Arrange: que el cliente se suscriba a esperar taxista
        StompSession stompSessionCliente = obtenerSesionWS(authHeaderCliente);
        stompSessionCliente.subscribe("/user/queue/esperar-taxista", new ViajeControladorIntegrationTest.CreateStringStompFrameHandlerTaxistaAsignado());

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
