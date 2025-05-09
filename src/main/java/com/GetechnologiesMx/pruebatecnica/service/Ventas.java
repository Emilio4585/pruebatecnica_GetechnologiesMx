package com.GetechnologiesMx.pruebatecnica.service;


import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.repository.FacturaRepository;
import com.GetechnologiesMx.pruebatecnica.repository.PersonaRepository;
import com.GetechnologiesMx.pruebatecnica.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class Ventas {

    private final FacturaRepository facturaRepository;
    private final PersonaRepository personaRepository;

    public Ventas(FacturaRepository facturaRepository, PersonaRepository personaRepository) {
        this.facturaRepository = facturaRepository;
        this.personaRepository = personaRepository;
    }

    // Buscar todas las facturas asociadas a una persona
    public List<Factura> findFacturasByPersona(String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró persona con identificación: " + identificacion));
        return facturaRepository.findByPersona(persona);
    }

    // Crear una nueva factura para una persona
    public Factura storeFactura(Factura factura, String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró persona con identificación: " + identificacion));
        factura.setPersona(persona);
        return facturaRepository.save(factura);
    }
}
