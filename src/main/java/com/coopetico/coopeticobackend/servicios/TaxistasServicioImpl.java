package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaxistasServicioImpl implements  TaxistasServicio {

    @Autowired
    private TaxistasRepositorio taxistaRepositorio;

    @Override
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TaxistaEntidad> consultar(){
        return taxistaRepositorio.consultar();
    }

    @Override
    @Transactional
    public TaxistaEntidad guardar(TaxistaEntidad taxista){
        return taxistaRepositorio.save(taxista);
    }

    @Override
    @Transactional
    public TaxistaEntidad consultarPorId(String idCorreoUsuario){
        return taxistaRepositorio.findById(idCorreoUsuario).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(String correoUsuario){
        taxistaRepositorio.deleteById(correoUsuario);
    }

}
