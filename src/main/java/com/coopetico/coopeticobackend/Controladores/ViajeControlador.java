package com.coopetico.coopeticobackend.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import com.coopetico.coopeticobackend.repositorios.ViajesRepositorio;
import com.coopetico.coopeticobackend.entidades.ViajeEntidad;

/*
 * TO_DO: comentar según las convenciones.
 */
@Controller
@RequestMapping(path="/viajes")
public class ViajeControlador {
	private final ViajesRepositorio VIAJES;

	/*
	 * TO_DO: comentar según las convenciones.
	 */
	@Autowired
    public ViajeControlador(ViajesRepositorio vjs) {
        this.VIAJES = vjs;
    }

	/*
	 * TO_DO: comentar según las convenciones.
	 */
    @GetMapping/*TO_DO: PostMapping*/(path="/agregar_viaje")
    public @ResponseBody String agregar_viaje (
    	String 		placa, 
    	String 		correo_cliente, 
    	Timestamp	fecha_inicio, 
    	Timestamp	fecha_fin,
    	String 		costo,
    	Integer 	estrellas,
    	String 		origen_destino,
    	String 		correo_taxista
    ) {
    	// ViajeEntidadPK viajeEntidadPK,
	    // Timestamp fechaFin,
	    // String costo,
	    // Integer estrellas,
	    // String origenDestino,
	    // TaxiEntidad taxiByPkPlacaTaxi,
	    // ClienteEntidad clienteByPkCorreoCliente,
	    // TaxistaEntidad taxistaByCorreoTaxi
        return "test1 + test2";
    }
}