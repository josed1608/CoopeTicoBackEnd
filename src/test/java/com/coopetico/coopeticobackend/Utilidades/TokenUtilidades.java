package com.coopetico.coopeticobackend.Utilidades;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Service
public class TokenUtilidades {
    private HttpHeaders tokenCliente;
    private HttpHeaders tokenGerente;
    private HttpHeaders tokenAdministrativo;
    private HttpHeaders[] tokensTaxistas = new HttpHeaders[2];

    /**
     * Método para generar el token correspondiente al usuario y contraseña dados, devuelve el header de autenticación ya armado
     *
     * @param usuario correo del usuario
     * @param contrasenna contraseña
     * @return retorna el header para autorizarse
     */
    private HttpHeaders obtenerToken(String usuario, String contrasenna) throws Exception {
        MvcResult result = MockMvcUtilidades.getMockMvc().perform(post("/auth/signin")
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

    public HttpHeaders obtenerTokenCliente() throws Exception {
        if(tokenCliente == null)
            tokenCliente =  obtenerToken("cliente@cliente.com", "contrasenna");
        return tokenCliente;
    }

    public HttpHeaders obtenerTokenTaxista(int numero) throws Exception {
        if(tokensTaxistas[numero-1] == null)
            tokensTaxistas[numero-1] = obtenerToken("taxista" + numero + "@taxista.com", "contrasenna");
        return tokensTaxistas[numero-1];
    }

    public HttpHeaders obtenerTokenAdministrativo() throws Exception {
        if (tokenAdministrativo == null)
            return obtenerToken("administrativo@administrativo.com", "contrasenna");
        return tokenAdministrativo;
    }

    public HttpHeaders obtenerTokenGerente() throws Exception {
        if (tokenGerente == null)
            return obtenerToken("gerente@gerente.com", "contrasenna");
        return tokenGerente;
    }
}
