package com.coopetico.coopeticobackend.servicios.integration;

import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteServicioIntegrationTest {

    @Autowired
    ClienteServicio clienteServicio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ClientesRepositorio clientesRepositorio;

    @Before
    public void setup() {
    }

    @Test
    @Transactional
    public void crearCliente() throws Exception {
        //Arrange
        try {
            clienteServicio.borrarCliente("prueba@prueba.com");
        }
        catch (UsuarioNoEncontradoExcepcion ignored) {}
        finally {
            UsuarioEntidad usuario = new UsuarioEntidad("prueba@prueba.com", "nombre", "apellido1", "apellido2", "telefono", "contrasenna", "", true,null, null, null, null);
            usuarioServicio.agregarUsuario(usuario, "Cliente");

            //Act
            clienteServicio.agregarCliente(usuario);

            //Assert
           assertThat(clientesRepositorio.findById(usuario.getPkCorreo()).isPresent(), is(true));
        }
    }
}
