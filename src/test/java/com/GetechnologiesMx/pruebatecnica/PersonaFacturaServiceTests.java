package com.GetechnologiesMx.pruebatecnica;

import org.junit.jupiter.api.Test;

import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.repository.FacturaRepository;
import com.GetechnologiesMx.pruebatecnica.repository.PersonaRepository;
import com.GetechnologiesMx.pruebatecnica.service.Directorio;
import com.GetechnologiesMx.pruebatecnica.service.Ventas;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonaFacturaServiceTests {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    private Directorio directorio;
    private Ventas ventas;

    @BeforeEach
    public void setup() {
        directorio = new Directorio(personaRepository, facturaRepository);
        ventas = new Ventas(facturaRepository, personaRepository);
    }

    @Test
    public void testCrearYBuscarPersona() {
        Persona persona = new Persona("Juan", "Pérez", "Gómez", "ABC12345");
        directorio.storePersona(persona);

        Persona encontrada = directorio.findPersonaByIdentificacion("ABC12345");
        assertNotNull(encontrada);
        assertEquals("Juan", encontrada.getNombre());
    }

    @Test
    public void testEliminarPersonaYFacturas() {
        Persona persona = new Persona("Ana", "López", null, "XYZ789");
        personaRepository.save(persona);

        Factura factura = new Factura(LocalDate.now(), 500.0, persona);
        facturaRepository.save(factura);

        directorio.deletePersonaByIdentificacion("XYZ789");

        Optional<Persona> eliminada = personaRepository.findByIdentificacion("XYZ789");
        assertTrue(eliminada.isEmpty());

        List<Factura> facturas = facturaRepository.findByPersona(persona);
        assertTrue(facturas.isEmpty());
    }

    @Test
    public void testCrearFacturaParaPersona() {
        Persona persona = new Persona("Carlos", "Ramírez", "Torres", "DEF45678");
        personaRepository.save(persona);

        Factura nuevaFactura = new Factura(LocalDate.of(2025, 5, 8), 2500.0, null);
        Factura creada = ventas.storeFactura(nuevaFactura, "DEF45678");

        assertNotNull(creada);
        assertEquals(2500.0, creada.getMonto());
        assertEquals("DEF45678", creada.getPersona().getIdentificacion());
    }

    @Test
    public void testBuscarFacturasPorPersona() {
        Persona persona = new Persona("Lucía", "Martínez", "Soto", "LM2025");
        personaRepository.save(persona);

        facturaRepository.save(new Factura(LocalDate.now(), 1500.0, persona));
        facturaRepository.save(new Factura(LocalDate.now(), 3000.0, persona));

        List<Factura> facturas = ventas.findFacturasByPersona("LM2025");
        assertEquals(2, facturas.size());
    }
}
