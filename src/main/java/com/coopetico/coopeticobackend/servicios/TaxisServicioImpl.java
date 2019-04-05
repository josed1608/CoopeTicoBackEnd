package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaxisServicioImpl implements TaxisServicio{
    @Autowired
    private TaxisRepositorio taxisRepositorio;

    @Override
    @Transactional
    public List<TaxiEntidad> consultar(){
        return (List<TaxiEntidad>) taxisRepositorio.findAll();
    }

    @Override
    @Transactional
    public TaxiEntidad consultarPorId(String placa){
        return taxisRepositorio.findById(placa).orElse(null);
    }

    @Override
    @Transactional
    public TaxiEntidad guardar(TaxiEntidad taxi){
        return taxisRepositorio.save(taxi);
    }

    @Override
    @Transactional
    public void eliminar(String placa){
        taxisRepositorio.deleteById(placa);
    }
}
