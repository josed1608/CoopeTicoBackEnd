package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.OperadorEntidad;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 Interfaz del servicio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface OperadoresServicio {
    OperadorEntidad consultarPorId(String correo);
}
