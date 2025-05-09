package com.GetechnologiesMx.pruebatecnica.service;

import com.GetechnologiesMx.pruebatecnica.exception.ResourceNotFoundException;
import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.repository.FacturaRepository;
import com.GetechnologiesMx.pruebatecnica.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class Directorio {
    private static final Logger log = LoggerFactory.getLogger(Directorio.class);
    private final PersonaRepository personaRepository;
    private final FacturaRepository facturaRepository;

    public Directorio(PersonaRepository personaRepository, FacturaRepository facturaRepository) {
        this.personaRepository = personaRepository;
        this.facturaRepository = facturaRepository;
    }

    public Persona findPersonaByIdentificacion(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada: " + identificacion));
    }

    public List<Persona> findPersonas() {
        return personaRepository.findAll();
    }

   @Transactional
    public void deletePersonaByIdentificacion(String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada: " + identificacion));
        
        // También se eliminan las facturas si hay relación
        facturaRepository.deleteByPersona(persona);
        personaRepository.delete(persona);
        
        log.info("Persona eliminada: {}", identificacion);
    }


    public Persona storePersona(Persona persona) {
        // Validación de duplicados
        if (personaRepository.findByIdentificacion(persona.getIdentificacion()).isPresent()) {
            log.warn("Intento de crear persona duplicada: {}", persona.getIdentificacion());
            throw new DuplicateResourceException(
                "Ya existe una persona con identificación: " + persona.getIdentificacion()
            );
        }
        log.info("Guardando persona: {}", persona.getIdentificacion());
        return personaRepository.save(persona);
    }
}