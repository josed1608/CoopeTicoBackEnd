package com.coopetico.coopeticobackend.Utilidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Service
public class MockMvcUtilidades {
    WebApplicationContext context;

    private static MockMvc mockMvc;

    @Autowired
    public MockMvcUtilidades(WebApplicationContext context) {
        this.context = context;
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }

    public static MockMvc getMockMvc(){
        return mockMvc;
    }
}
