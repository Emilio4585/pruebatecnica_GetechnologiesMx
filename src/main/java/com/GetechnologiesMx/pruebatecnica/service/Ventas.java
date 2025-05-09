package com.GetechnologiesMx.pruebatecnica.service;


import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Ventas {
    private final FacturaRepository facturaRepository;

    public Ventas(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> findFacturasByPersona(Persona persona) {
        return facturaRepository.findByPersona(persona);
    }

    public Factura storeFactura(Factura factura) {
        return facturaRepository.save(factura);
    }
}