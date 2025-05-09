package com.GetechnologiesMx.pruebatecnica.service;


import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class Directorio {
    private static final Logger log = LoggerFactory.getLogger(Directorio.class);
    private final PersonaRepository personaRepository;

    public Directorio(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona findPersonaByIdentificacion(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada: " + identificacion));
    }

    public List<Persona> findPersonas() {
        return personaRepository.findAll();
    }

    public void deletePersonaByIdentificacion(String identificacion) {
        personaRepository.deleteByIdentificacion(identificacion);
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