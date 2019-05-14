package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.TaxiTemporal;

import java.util.ArrayList;
import java.util.List;

public class UbicacionTaxiControladorUnitTest {
    public TaxiEntidad obtenerTaxi(List<TaxiEntidad> taxis, String placa){
        return new TaxiEntidad();
    }

    public List<TaxiTemporal> asociarTaxis(){
        return new ArrayList<TaxiTemporal>();
    }

    public List<TaxiTemporal> getTaxis(){
        return new ArrayList<TaxiTemporal>();

    }
}
