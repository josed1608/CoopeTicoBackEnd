package com.coopetico.coopeticobackend.Controladores;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxistas")
public class TaxistasControlador {

    @GetMapping("/insertarTaxista")
    public String insertarTaxista() {
        return "PRUEBA DE INSERTAR";
    }

    @GetMapping("/consultarTaxista/{pkCorreoUsuario}")
    public TaxiEntidad consultarTaxista(@PathVariable String pkCorreoUsuario) {
        return null ;
    }


}