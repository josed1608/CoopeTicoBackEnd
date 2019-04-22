package com.coopetico.coopeticobackend.Controladores;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import com.coopetico.coopeticobackend.servicios.TaxisServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/taxis")
public class TaxisControlador {

    @Autowired
    private TaxisServicio taxisServicio;

    @GetMapping("/taxis")
    public List<TaxiEntidad> consultar(){
        return taxisServicio.consultar();
    }

    @GetMapping("/taxis/{id}")
    public TaxiEntidad consultarPorId(@PathVariable String id){
        return taxisServicio.consultarPorId(id);
    }

    @PostMapping("/taxis")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxiEntidad agregar(@RequestBody TaxiEntidad taxi){

        return taxisServicio.guardar(taxi);
    }

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

    @DeleteMapping("/taxis/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id){
        taxisServicio.eliminar(id);
    }
}
