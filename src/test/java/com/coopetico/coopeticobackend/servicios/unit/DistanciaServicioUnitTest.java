package com.coopetico.coopeticobackend.servicios.unit;

import com.coopetico.coopeticobackend.servicios.DistanciaServicio;
import com.coopetico.coopeticobackend.servicios.DistanciaServicioImpl;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest({DistanceMatrixApiRequest.class, DistanceMatrixApi.class, GeoApiContext.Builder.class, DistanciaServicioImpl.class})
public class DistanciaServicioUnitTest {
    private DistanciaServicioImpl distanciaServicio;

    @Before
    public void setup() throws Exception{
        DistanceMatrixApiRequest request = mock(DistanceMatrixApiRequest.class);
        when(request.origins(any(LatLng.class))).thenReturn(request);
        when(request.destinations(any(LatLng.class))).thenReturn(request);
        when(request.mode(any(TravelMode.class))).thenReturn(request);
        when(request.units(any(Unit.class))).thenReturn(request);
        when(request.await()).thenReturn(new DistanceMatrix(new String[0], new String[0], new DistanceMatrixRow[0]));

        GeoApiContext.Builder b = Mockito.mock(GeoApiContext.Builder.class, Mockito.RETURNS_SELF);
        PowerMockito.whenNew(GeoApiContext.Builder.class).withNoArguments().thenReturn(b);

        PowerMockito.mockStatic(DistanceMatrixApi.class);
        BDDMockito.given(DistanceMatrixApi.newRequest(nullable(GeoApiContext.class))).willReturn(request);
        distanciaServicio = new DistanciaServicioImpl();
    }

    @Test
    public void unitTestTaxistaMasCercano() throws  Exception{
        LatLng origen  = new LatLng(9.963621, -84.067743);
        LatLng destino1 = new LatLng(9.963144, -84.054909);
        LatLng destino2 = new LatLng(9.957288, -84.039617);

        List<Pair<String, LatLng>> taxistas = new LinkedList<>();
        taxistas.add(Pair.of("taxi2", destino2));
        taxistas.add(Pair.of("taxi1", destino1));

        Assert.assertEquals( "",distanciaServicio.taxistaMasCercano(origen, taxistas));
    }

}
