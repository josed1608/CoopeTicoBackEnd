package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.AuthControlador;
import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthControladorUnitTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    AuthControlador authController;
    @MockBean
    UsuarioServicio usuarioService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.authController).build();// Standalone context
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        // .build();
    }

    @Test
    public void testSignIn() throws Exception {
        //Arrange
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        UsuarioEntidad mockUsuario = new UsuarioEntidad();
        mockUsuario.setGrupoByIdGrupo(new GrupoEntidad("rol", null, null));
        when(usuarioService.usuarioPorCorreo(any(String.class))).thenReturn(Optional.of(mockUsuario));
        when(usuarioService.obtenerPermisos(any(UsuarioEntidad.class))).thenReturn(new LinkedList<>());
        when(jwtTokenProvider.createToken(any(String.class), any(List.class), any(String.class))).thenReturn("");

        //Act
        mockMvc.perform(post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{" +
                                "\"username\": \"prueba\"," +
                                "\"password\": \"contrasenna1\"" +
                             "}"))
                //Assert
                .andExpect(status().isOk());
    }
}