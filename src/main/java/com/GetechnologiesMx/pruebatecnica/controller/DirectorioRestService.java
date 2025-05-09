package com.GetechnologiesMx.pruebatecnica.controller;

import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.service.Directorio;
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
    public List<Persona> getAllPersonas() {
        return directorio.findPersonas();
    }

    @GetMapping("/{identificacion}")
    public Persona getPersona(@PathVariable String identificacion) {
        return directorio.findPersonaByIdentificacion(identificacion);
    }

    @PostMapping
    public Persona createPersona(@RequestBody Persona persona) {
        return directorio.storePersona(persona);
    }

    @DeleteMapping("/{identificacion}")
    public void deletePersona(@PathVariable String identificacion) {
        directorio.deletePersonaByIdentificacion(identificacion);
    }
}