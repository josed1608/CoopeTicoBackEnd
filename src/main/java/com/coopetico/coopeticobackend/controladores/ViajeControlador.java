//-----------------------------------------------------------------------------
package com.coopetico.coopeticobackend.controladores;
//-----------------------------------------------------------------------------
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.entidades.ViajeDatosIniciales;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import com.coopetico.coopeticobackend.entidades.ViajeTmpEntidad;

import org.springframework.http.HttpStatus;
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
 * Este es el controlador Viajes. Se encarga de la comunicación entre
 * la capa V y el M viajes.
 *
 * @autor Joseph Rementería (b55824)
 * @since 06-04-2019
 **/
public class ViajeControlador {
    //-------------------------------------------------------------------------
    // Variables globales.
    private ViajesServicio viajesServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    //-------------------------------------------------------------------------
    // Métodos.
    /**
     * Constructor de la clase.
     *
     * @author Joseph Rementería (b55824)
     * @since 06-04-2019
     *
     * @param vjsRep repositorio de viajes ya creado
     *
     */
    @Autowired
    public ViajeControlador(ViajesServicio vjsRep) {
        this.viajesServicio = vjsRep;
    }

    /**
     * Guarda una tupla en la base de datos.
     *
     * @author Joseph Rementería (b55824)
     * @since 06-04-2019
     *
     * @param viajeTempEntidad  entidad dummy crada para recibir los datos
     *                          del viaje del front end
     * @return String se inserto, null de otra manera.
     */
    @PostMapping("viajeCompleto")
    public ResponseEntity agregarViaje (@RequestBody ViajeTmpEntidad viajeTempEntidad) {
        try  {
            this.viajesServicio.guardar(
                viajeTempEntidad.getPlaca(),
                viajeTempEntidad.getCorreoCliente(),
                viajeTempEntidad.getFechaInicio(),
                viajeTempEntidad.getFechaFin(),
                viajeTempEntidad.getCosto(),
                viajeTempEntidad.getEstrellas(),
                viajeTempEntidad.getOrigen(),
                viajeTempEntidad.getDestino(),
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
    public List<ViajeEntidadTemporal> obtenerViajes(){
        ViajeEntidadTemporal viajeEntidadTemporal = new ViajeEntidadTemporal();
        List<ViajeEntidadTemporal> listaTemporal = viajeEntidadTemporal.convertirListaViajes(viajesServicio.consultarViajes());
        for (ViajeEntidadTemporal viajeTemporal : listaTemporal) {
            viajeTemporal.setNombreCliente(this.obtenerNombreUsuario(viajeTemporal.getCorreoCliente()));
            viajeTemporal.setNombreTaxista(this.obtenerNombreUsuario(viajeTemporal.getCorreoTaxista()));
            viajeTemporal.setNombreOperador(this.obtenerNombreUsuario(viajeTemporal.getCorreoOperador()));
        }
        return listaTemporal;
    }

    /**
     * Método que permite obtener el nombre completo de un usuario.
     * @param correo Id Usuario
     * @return Nombre y apellidos
     */
    private String obtenerNombreUsuario(String correo){
        UsuarioEntidad usuarioTemporal = usuarioServicio.usuarioPorCorreo(correo).get();
        return usuarioTemporal.getNombre() + ' ' + usuarioTemporal.getApellido1() + ' ' + usuarioTemporal.getApellido2();
    }


    /**
     * Crea un registro en la base de datos con los datos del viaej disponibles
     * para cuando se cominza un viaje.
     *
     * @author Joseph Rementería (b55824)
     * @since 11-05-2019
     *
     * @param datosDelViaje todos los datos del viaje
     * @return  Ok si no hubo problema,
     *          Not found si el usuario que crea el viaje no existe en la DB
     *          Server error, si el error no ha sido identificado.
     */
    @PostMapping()
    public ResponseEntity crearViaje(@RequestBody ViajeDatosIniciales datosDelViaje) {
        //---------------------------------------------------------------------
        ResponseEntity result = null;
        //---------------------------------------------------------------------
        // Se intenta insertar la entidad en la base de datos
        int respuestaRepo = this.viajesServicio.crear(
            datosDelViaje.getPlaca(),
            datosDelViaje.getFechaInicio(),
            datosDelViaje.getCorreoCliente(),
            datosDelViaje.getOrigen(),
            datosDelViaje.getCorreoTaxista()
        );
        //---------------------------------------------------------------------
        // Se genera la respuesta HTTP correspondiente al código de error/éxito
        switch (respuestaRepo){
            case 0:
                result = new ResponseEntity(
                        "Se insetó el viaje",
                        HttpStatus.OK
                );
                break;
            case -1:
                result = new ResponseEntity(
                        "Hubo un error no identificado",
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
                break;
            case -2:
                result = new ResponseEntity(
                        "El usuario (cliente ni operador) no existe",
                        HttpStatus.NOT_FOUND
                );
                break;
            case -3:
                result = new ResponseEntity(
                  "No se se pudo insertar en la base",
                  HttpStatus.INTERNAL_SERVER_ERROR
                );
            case -4:
                result = new ResponseEntity(
                    "El dato ya ha sido insertado previamente",
                    HttpStatus.CONFLICT
                );
                break;
        }
        //---------------------------------------------------------------------
        return result;
        //---------------------------------------------------------------------
    }
    //-------------------------------------------------------------------------
}
//-----------------------------------------------------------------------------