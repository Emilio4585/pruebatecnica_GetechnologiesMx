package com.GetechnologiesMx.pruebatecnica.controller;


import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.service.Directorio;
import com.GetechnologiesMx.pruebatecnica.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ApiResponse<List<Persona>> response = new ApiResponse<>(
            200,
            "Personas listadas",
            personas,
            null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<Persona>> getPersona(
            @PathVariable String identificacion) {
        Persona p = directorio.findPersonaByIdentificacion(identificacion);
        ApiResponse<Persona> response = new ApiResponse<>(
            200,
            "Persona obtenida: " + identificacion,
            p,
            null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Persona>> createPersona(
            @RequestBody Persona persona) {
        Persona creada = directorio.storePersona(persona);
        ApiResponse<Persona> response = new ApiResponse<>(
            201,
            "Persona creada: " + creada.getIdentificacion(),
            creada,
            null
        );
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<Void>> deletePersona(
            @PathVariable String identificacion) {
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
