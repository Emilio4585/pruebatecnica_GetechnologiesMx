package com.GetechnologiesMx.pruebatecnica.controller;

import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.model.Persona;
import com.GetechnologiesMx.pruebatecnica.service.Directorio;
import com.GetechnologiesMx.pruebatecnica.service.Ventas;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestService {
    private final Ventas ventas;
    private final Directorio directorio;

    public FacturaRestService(Ventas ventas, Directorio directorio) {
        this.ventas = ventas;
        this.directorio = directorio;
    }

    @GetMapping("/{identificacion}")
    public List<Factura> getFacturasByPersona(@PathVariable String identificacion) {
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        return ventas.findFacturasByPersona(persona);
    }

    @PostMapping
    public Factura createFactura(@RequestBody Factura factura) {
        return ventas.storeFactura(factura);
    }
}