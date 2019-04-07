/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * <p>
 * Fecha: 06/04/2019.
 * <p>
 * Este es el controlador Viajes. Se encarga de la comunicación entre
 * la capa V y el M viajes.
 **/
package com.coopetico.coopeticobackend.controladores;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


@Controller
@RequestMapping(path = "/api")
public class ViajeControlador {
    /**
     * Variables globales.
     */
    private ViajesServicio viajesRepositorio;

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Cosntructor de la clase
     *
     * @param vjsRep repositorio de viajes ya creado
     *
     */
    @Autowired
    public ViajeControlador(ViajesServicio vjsRep) {
        this.viajesRepositorio = vjsRep;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Gaurda una tupla en la base de datos.
     *
     * @param placa
     * @param correo_cliente
     * @param fecha_inicio
     * @param fecha_fin
     * @param costo
     * @param estrellas
     * @param origen_destino
     * @param correo_taxista
     * @return
     */
    @GetMapping/*TO_DO: PostMapping*/(path = "/viajes/{placa}...")
    public @ResponseBody
    String agregarViaje(
            String placa,
            String correo_cliente,
            Timestamp fecha_inicio,
            Timestamp fecha_fin,
            String costo,
            Integer estrellas,
            String origen_destino,
            String correo_taxista
    ) {
        return this.viajesRepositorio.guardar(
                placa,
                correo_cliente,
                fecha_inicio,
                fecha_fin,
                costo,
                estrellas,
                origen_destino,
                correo_taxista
        );
    }
}