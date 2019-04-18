package com.coopetico.coopeticobackend;

import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TokensRecuperacionContrasenaServicioTest {
    @Autowired
    private TokensRecuperacionContrasenaServicio tokensServicio;

    @Test
    public void insertarTokenCorreoValido() {
        // given
        String correo = "cliente@cliente.com";
        String resultado;
        // when
        try {
            resultado = tokensServicio.insertarToken(correo);
        }catch (Exception e){
            resultado = null;
        }

        // then
        assertThat(resultado).isNotNull();
    }

    @Test
    public void insertarTokenCorreoFormatoInvalido() {
        // given
        String correo = "clientecliente.com";
        String resultado;
        // when
        try {
            resultado = tokensServicio.insertarToken(correo);
        }catch (Exception e){
            resultado = null;
        }
        // then
        assertThat(resultado).isNull();
    }

    @Test
    public void insertarTokenCorreoInvalido() {
        // given
        String correo = "kevina@gmail.com";
        String resultado;
        // when
        try {
            resultado = tokensServicio.insertarToken(correo);
        }catch (Exception e){
            resultado = null;
        }

        // then
        assertThat(resultado).isNull();
    }
}