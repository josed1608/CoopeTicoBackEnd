package com.coopetico.coopeticobackend.controladores.integration;

import com.coopetico.coopeticobackend.controladores.AuthControlador;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    AuthControlador authControlador;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.authControlador).build();
    }

    @Test
    @Transactional
    public void testLoginSuccesfull() throws Exception {
        mockMvc.perform(post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{" +
                        "\"username\": \"cliente@cliente.com\"," +
                        "\"password\": \"contrasenna\"" +
                        "}"))
                .andExpect(status().isOk());
    }
}
