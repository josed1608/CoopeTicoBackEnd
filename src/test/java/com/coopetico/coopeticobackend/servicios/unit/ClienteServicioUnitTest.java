package com.coopetico.coopeticobackend.servicios.unit;

import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.ClientesRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteServicioUnitTest {
    @Autowired
    ClienteServicio clienteServicio;

    @MockBean
    ClientesRepositorio clientesRepositorio;
    @MockBean
    UsuariosRepositorio usuariosRepositorio;

    @Test
    public void crearCliente(){
        when(usuariosRepositorio.findById(any(String.class))).thenReturn(Optional.of(new UsuarioEntidad()));
        when(clientesRepositorio.save(any(ClienteEntidad.class))).thenReturn(null);

        UsuarioEntidad usuarioPrueba = new UsuarioEntidad();
        usuarioPrueba.setPkCorreo("correo");
        clienteServicio.agregarCliente(usuarioPrueba);
    }

}
