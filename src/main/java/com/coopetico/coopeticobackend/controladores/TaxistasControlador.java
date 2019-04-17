package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 Controlador de la entidad Taxista para consultar, insertar, modificar y eliminar taxistas.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TaxistasControlador {

    /**
     * Servicio de taxistas para consultar datos.
     */
    @Autowired
    private TaxistasServicio taxistaServicio;

    /**
     * Funcion que obtiene los taxistas existentes en el sistema.
     * @return Lista de taxistas, correo, nombre, apellidos, telefono y estado.
     */
    @GetMapping("/taxistas")
    public List<TaxistaEntidadTemporal> consultar() {
        return taxistaServicio.consultar();
    }

    /**
     * Funcion que obtiene un taxista.
     * @param correoUsuario correo del taxista.
     * @return Lista de taxistas, correo, nombre, apellidos, telefono y estado.
     */
    @GetMapping("/taxistas/{correoUsuario}")
    public TaxistaEntidadTemporal consultarPorId(@PathVariable String correoUsuario) {
        return taxistaServicio.consultarPorId(correoUsuario);
    }

    /**
     * Funcion que agrega un taxista.
     * @param taxista Taxista que se desea agregar.
     * @return Taxista agregado.
     */
    @PostMapping("/taxistas")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidadTemporal agregar(@RequestBody TaxistaEntidadTemporal taxista){
        return taxistaServicio.guardar(taxista, taxista.getPkCorreoUsuario());
    }

    /**
     * Funcion que modifica un taxista.
     * @param taxista Taxista que se desea modificar.
     * @param pkCorreoUsuario Correo del taxista que se desea modificar.
     * @return Taxista modificado.
     */
    @PutMapping("/taxistas/{pkCorreoUsuario}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidadTemporal modificar(@RequestBody TaxistaEntidadTemporal taxista, @PathVariable("pkCorreoUsuario") String pkCorreoUsuario){
        return taxistaServicio.guardar(taxista, pkCorreoUsuario);
    }

    /**
     * Metodo que elimina un taxista.
     * @param correoUsuario Correo del taxista que se desea eliminar.
     */
    @DeleteMapping("/taxistas/{correoUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String correoUsuario){
        taxistaServicio.eliminar(correoUsuario);
    }

}