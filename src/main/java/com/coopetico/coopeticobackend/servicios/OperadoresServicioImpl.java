package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.*;
import com.coopetico.coopeticobackend.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

/**
 Servicio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
@Service
public class OperadoresServicioImpl implements  OperadoresServicio {
    @Autowired
    private OperadorRepositorio operadoresRepositorio;

    public OperadoresServicioImpl() { }

    @Override
    @Transactional
    public OperadorEntidad consultarPorId(String correo) {
        return this.operadoresRepositorio.findById(correo).orElse(null);
    }


}
