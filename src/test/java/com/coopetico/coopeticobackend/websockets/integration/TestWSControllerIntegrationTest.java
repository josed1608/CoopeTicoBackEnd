package com.coopetico.coopeticobackend.websockets.integration;

import com.coopetico.coopeticobackend.controladores.AuthControlador;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestWSControllerIntegrationTest {
    private MockMvc mockMvc;
    @Autowired
    AuthControlador authControlador;

    @LocalServerPort
    private int port;
    private String URL;
    private HttpHeaders authHeader;

    private CompletableFuture<String> completableFuture;

    /**
     * Se guarda el url para conectarse al webscoket, se inicia sesión y se toma el token para poder autenticarse al usar el websocket
     */
    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.authControlador).build();

        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/ws";
        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\": \"cliente@cliente.com\"," +
                        "\"password\": \"contrasenna\"" +
                        "}"))
                .andReturn();
        String token = result.getResponse().getContentAsString();
        authHeader = new HttpHeaders();
        authHeader.add("Authorization", "Bearer " + token);
    }

    /**
     * Prueba que el endpoint de subscripción de aplicación /app/test devuelve
     */
    @Test
    @Transactional
    public void testSubcscribeTest() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(authHeader), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe("/app/test", new CreateStringStompFrameHandler());

        String respueste = completableFuture.get(10, SECONDS);

        assertNotNull(respueste);
    }

    /**
     * Prueba el endpoint para los destinos de usuario
     */
    @Test
    @Transactional
    public void testSendTest() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        //Use the MappingJackson2MessageConverter when the response is a JSON
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(authHeader),new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe("/user/queue/test", new CreateStringStompFrameHandler());
        stompSession.send("/app/user/queue/test", "hola");

        String respueste = completableFuture.get(10, SECONDS);

        assertNotNull(respueste);
    }

    /**
     * Prueba el endpoint para el topic test
     */
    @Test
    @Transactional
    public void testSendTopic() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        //Use the MappingJackson2MessageConverter when the response is a JSON
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(authHeader),new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe("/topic/test", new CreateStringStompFrameHandler());
        stompSession.send("/topic/test", "hola");

        String respueste = completableFuture.get(10, SECONDS);

        assertNotNull(respueste);
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
    private class CreateStringStompFrameHandler implements StompFrameHandler {
        //Devuelve el tipo de los mensajes
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return String.class;
        }

        //Toma el mensaje y lo agrega al CompletableFuture para que el método del test lo vea
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((String) o);
        }
    }
}
