//-----------------------------------------------------------------------------
package com.coopetico.coopeticobackend.controladores;
//-----------------------------------------------------------------------------
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import com.coopetico.coopeticobackend.entidades.ViajeTmpEntidad;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
//-----------------------------------------------------------------------------
@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@Validated
@Controller
@RequestMapping(path = "/viajes")
/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * <p>
 * Fecha: 06/04/2019.
 * <p>
 * Este es el controlador Viajes. Se encarga de la comunicación entre
 * la capa V y el M viajes.
 **/
public class ViajeControlador {
    //-------------------------------------------------------------------------
    // Variables globales.
    private ViajesServicio viajesServicio;
    //-------------------------------------------------------------------------
    // Métodos.
    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Constructor de la clase.
     *
     * @param vjsRep repositorio de viajes ya creado
     *
     */
    @Autowired
    public ViajeControlador(ViajesServicio vjsRep) {
        this.viajesServicio = vjsRep;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Gaurda una tupla en la base de datos.
     *
     * @param viajeTempEntidad entidad dummy crada para recibir los datos 
     * del viaje del front end
     * @return String se inserto, null de otra manera.
     */
    @PostMapping()
    public ResponseEntity agregarViaje (@RequestBody ViajeTmpEntidad viajeTempEntidad) {
        try  {
            this.viajesServicio.guardar(
                viajeTempEntidad.getPlaca(),
                viajeTempEntidad.getCorreoCliente(),
                viajeTempEntidad.getFechaInicio(),
                viajeTempEntidad.getFechaFin(),
                viajeTempEntidad.getCosto(),
                viajeTempEntidad.getEstrellas(),
                viajeTempEntidad.getOrigenDestino(),
                viajeTempEntidad.getOrigenDestino(),
                viajeTempEntidad.getCorreoTaxista()
            );
            return ok("Viaje agregado");
        } catch (Exception e){
            return ok("error");
        }
    }


    /**
     * Metodo para obtener una lista de usuarios
     * @return Lista de usuarios
     */
    @GetMapping()
    public List<ViajeEntidad> obtenerViajes(){
        return viajesServicio.consultarViajes();
    }
}