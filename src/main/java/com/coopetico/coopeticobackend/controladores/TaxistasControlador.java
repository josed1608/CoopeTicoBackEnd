package com.coopetico.coopeticobackend.Controladores;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 Controlador de la entidad Taxista para consultar, insertar, modificar y eliminar taxistas.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version:    1.0.
 */

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
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TaxistaEntidad> consultar() {
        return taxistaServicio.consultar();
    }

    /**
     * Funcion que obtiene un taxista.
     * @param correoUsuario correo del taxista.
     * @return Lista de taxistas, correo, nombre, apellidos, telefono y estado.
     */
    @GetMapping("/taxistas/{correoUsuario}")
    public TaxistaEntidad consultarPorId(@PathVariable String correoUsuario) {
        return taxistaServicio.consultarPorId(correoUsuario);
    }

    /**
     * Funcion que agrega un taxista.
     * @param taxista Taxista que se desea agregar.
     * @return Taxista agregado.
     */
    @PostMapping("/taxistas")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidad agregar(@RequestBody TaxistaEntidad taxista){
        return taxistaServicio.guardar(taxista);
    }

    /**
     * Funcion que modifica un taxista.
     * @param taxista Taxista que se desea modificar.
     * @param correoUsuario Correo del taxista que se desea modificar.
     * @return Taxista modificado.
     */
    @PutMapping("/taxistas/{correoUsuario}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidad modificar(@RequestBody TaxistaEntidad taxista, @PathVariable String correoUsuario){
        TaxistaEntidad taxistaActual = taxistaServicio.consultarPorId(correoUsuario);
        taxistaActual.setEstado(taxista.isEstado());
        taxistaActual.setEstrellas(taxista.getEstrellas());
        taxistaActual.setFaltas(taxista.getFaltas());
        taxistaActual.setHojaDelincuencia(taxista.isHojaDelincuencia());
        taxistaActual.setPkCorreoUsuario(taxista.getPkCorreoUsuario());
        taxistaActual.setTaxiByPlacaTaxiDueno(taxista.getTaxiByPlacaTaxiDueno());
        taxistaActual.setTaxiByPlacaTaxiManeja(taxista.getTaxiByPlacaTaxiManeja());
        taxistaActual.setUsuarioByPkCorreoUsuario(taxista.getUsuarioByPkCorreoUsuario());
        taxistaActual.setViajesByPkCorreoUsuario(taxista.getViajesByPkCorreoUsuario());
        return taxistaServicio.guardar(taxistaActual);
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