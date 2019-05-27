//-----------------------------------------------------------------------------
// Package
package com.coopetico.coopeticobackend.servicios;
//-----------------------------------------------------------------------------
// Imports

import com.coopetico.coopeticobackend.entidades.bd.OperadorEntidad;
import com.coopetico.coopeticobackend.repositorios.OperadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
//-----------------------------------------------------------------------------
/**
 * Servicio de la entidad Operador.
 *
 * @author    Joseph Rementería (b55824).
 * @since     11-05-2019.
 * @version   1.0.
 */
@CrossOrigin(origins = "http://localhost:8080")
@Service
public class OperadoresServicioImpl implements  OperadoresServicio {
    //-------------------------------------------------------------------------
    // Variables globales
    @Autowired
    private OperadorRepositorio operadoresRepositorio;
    //-------------------------------------------------------------------------\
    // Constructor
    public OperadoresServicioImpl() { }
    //-----------------------------------------------------------------------------
    // Métodos
    @Override
    @Transactional
    public OperadorEntidad consultarPorId(String correo) {
        return this.operadoresRepositorio.findById(correo).orElse(null);
    }
    //-----------------------------------------------------------------------------
}
//-----------------------------------------------------------------------------