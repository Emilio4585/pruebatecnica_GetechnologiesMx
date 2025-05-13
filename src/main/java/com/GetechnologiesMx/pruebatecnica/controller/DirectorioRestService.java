package com.GetechnologiesMx.pruebatecnica.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.service.Directorio;
import com.GetechnologiesMx.pruebatecnica.web.ApiResponse;

@RestController
@RequestMapping("/api/personas")
public class DirectorioRestService {
    private final Directorio directorio;

    public DirectorioRestService(Directorio directorio) {
        this.directorio = directorio;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Persona>>> getAllPersonas() {
        List<Persona> personas = directorio.findPersonas();
        return ResponseEntity.ok(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Listado de personas",
                personas,
                null
            )
        );
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<Persona>> getPersona(
            @PathVariable String identificacion) {
        Persona p = directorio.findPersonaByIdentificacion(identificacion);
        return ResponseEntity.ok(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Persona obtenida: " + identificacion,
                p,
                null
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Persona>> createPersona(
            @RequestBody Persona persona) {
        // Validar identificacion no vacía
        if (persona.getIdentificacion() == null || persona.getIdentificacion().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Identificación obligatoria",
                    null,
                    "El campo 'identificacion' no puede estar vacío"
                )
            );
        }
        Persona creada = directorio.storePersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Persona creada: " + creada.getIdentificacion(),
                creada,
                null
            )
        );
    }

    @DeleteMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<Void>> deletePersona(
            @PathVariable String identificacion) {

        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        if (persona == null) {
            ApiResponse<Void> response = new ApiResponse<>(
                404,
                "Persona no encontrada: " + identificacion,
                null,
                "No se encontró la persona con la identificación proporcionada"
            );
            return ResponseEntity.status(404).body(response);
        }
        
        directorio.deletePersonaByIdentificacion(identificacion);
        ApiResponse<Void> response = new ApiResponse<>(
            200,
            "Persona eliminada: " + identificacion,
            null,
            null
        );
        return ResponseEntity.ok(response);
    }
}
