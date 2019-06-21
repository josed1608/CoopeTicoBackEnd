package com.coopetico.coopeticobackend.Utilidades;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Service
public class TokenUtilidades {
    /**
     * Método para generar el token correspondiente al usuario y contraseña dados, devuelve el header de autenticación ya armado
     *
     * @param usuario correo del usuario
     * @param contrasenna contraseña
     * @return retorna el header para autorizarse
     */
    private HttpHeaders obtenerToken(MockMvc mockMvc, String usuario, String contrasenna) throws Exception {
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

    public HttpHeaders obtenerTokenCliente(MockMvc mockMvc) throws Exception {
        return obtenerToken(mockMvc, "cliente@cliente.com", "contrasenna");
    }

    public HttpHeaders obtenerTokenTaxista(MockMvc mockMvc) throws Exception {
        return obtenerToken(mockMvc, "taxista@taxista.com", "contrasenna");
    }

    public HttpHeaders obtenerTokenAdministrativo(MockMvc mockMvc) throws Exception {
        return obtenerToken(mockMvc, "administrativo@administrativo.com", "contrasenna");
    }

    public HttpHeaders obtenerTokenGerente(MockMvc mockMvc) throws Exception {
        return obtenerToken(mockMvc, "gerente@gerente.com", "contrasenna");
    }
}
