package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de taxis
 * @autor   Jorge Araya González
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/taxis")
public class TaxisControlador {

    /**
     * Servicio de taxis
     */
    @Autowired
    private TaxisServicio taxisServicio;

    /**
     * Método para consultar todos los taxis de la base de datos
     * @return lista de entidades de taxi
     */
    @GetMapping("/taxis")
    public List<TaxiEntidad> consultar(){
        return taxisServicio.consultar();
    }

    /**
     * Método para consultar un taxi especifico
     * @param id placa del taxi a consultar
     * @return Entidad taxi que coincide con el id pasado por parámetro
     */
    @GetMapping("/taxis/{id}")
    public TaxiEntidad consultarPorId(@PathVariable String id){
        return taxisServicio.consultarPorId(id);
    }

    /**
     * Método para guardar un taxi nuevo en la base de datos
     * @param taxi entidad taxi que se quiere agregar
     * @return El taxi que se agregó a la base de datos
     */
    @PostMapping("/taxis")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxiEntidad agregar(@RequestBody TaxiEntidad taxi){

        return taxisServicio.guardar(taxi);
    }

    /**
     * Método para modificar un taxi existente en la base de datos
     * @param taxi Entidad taxi modificada
     * @param id Placa del taxi a modificar
     * @return Entidad del taxi modificado
     */
    @PutMapping("/taxis/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxiEntidad modificar(@RequestBody TaxiEntidad taxi, @PathVariable String id){
        TaxiEntidad taxiActual = taxisServicio.consultarPorId(id);
        taxiActual.setPkPlaca(taxi.getPkPlaca());
        taxiActual.setClase(taxi.getClase());
        taxiActual.setDatafono(taxi.getDatafono());
        taxiActual.setFechaVenMarchamo(taxi.getFechaVenMarchamo());
        taxiActual.setFechaVenSeguro(taxi.getFechaVenSeguro());
        taxiActual.setFechaVenTaxista(taxi.getFechaVenTaxista());
        taxiActual.setTelefono(taxi.getTelefono());
        taxiActual.setTipo(taxi.getTipo());

        return taxisServicio.guardar(taxiActual);
    }

    /**
     * Método para eliminar un taxi de la base de datos
     * @param id Placa del taxi a eliminar
     */
    @DeleteMapping("/taxis/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id){
        taxisServicio.eliminar(id);
    }
}
