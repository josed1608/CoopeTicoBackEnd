package com.coopetico.coopeticobackend.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping(path="/upload")
@Validated
/**
 * Clase encargada de manejar las principales funcionalidades con imagenes
 */
public class ImagenControlador {

    // Atributo para tener un log de los datos o errores
    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);

    /**
     * Metodo para obtener una imagen
     * @param nombreFoto Nombre de la imagen
     * @return Imagen
     */
    @GetMapping("/icon/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArchivo = Paths.get("images").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;

        log.info(rutaArchivo.toString());
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(!recurso.exists()&& !recurso.isReadable()){
            throw new RuntimeException("No se pudo cargar la imagen: "+ nombreFoto);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }

}
