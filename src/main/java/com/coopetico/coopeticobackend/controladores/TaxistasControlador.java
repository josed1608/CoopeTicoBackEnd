package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaxistasControlador {

    @Autowired
    private TaxistasServicio taxistaServicio;

    @GetMapping("/taxistas")
    public List<TaxistaEntidad> consultar() {
        return taxistaServicio.consultar();
    }

    @GetMapping("/taxistas/{correoUsuario}")
    public TaxistaEntidad consultarPorId(@PathVariable String correoUsuario) {
        return taxistaServicio.consultarPorId(correoUsuario);
    }

    @PostMapping("/taxistas")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidad agregar(@RequestBody TaxistaEntidad taxista){
        return taxistaServicio.guardar(taxista);
    }

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

    @DeleteMapping("/taxistas/{correoUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String correoUsuario){
        taxistaServicio.eliminar(correoUsuario);
    }

}