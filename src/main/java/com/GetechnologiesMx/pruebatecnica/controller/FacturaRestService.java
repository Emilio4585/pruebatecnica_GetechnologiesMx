package com.GetechnologiesMx.pruebatecnica.controller;

import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.service.Ventas;
import com.GetechnologiesMx.pruebatecnica.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestService {
    private final Ventas ventas;

    public FacturaRestService(Ventas ventas) {
        this.ventas = ventas;
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<List<Factura>>> getFacturasByPersona(
            @PathVariable String identificacion) {
        List<Factura> lista = ventas.findFacturasByPersona(identificacion);
        ApiResponse<List<Factura>> response = new ApiResponse<>(
            200,
            "Facturas obtenidas para: " + identificacion,
            lista,
            null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{identificacion}")
    public ResponseEntity<ApiResponse<Factura>> createFactura(
            @PathVariable String identificacion,
            @RequestBody Factura factura) {
        Factura creada = ventas.storeFactura(factura, identificacion);
        ApiResponse<Factura> response = new ApiResponse<>(
            201,
            "Factura creada para: " + identificacion,
            creada,
            null
        );
        return ResponseEntity.status(201).body(response);
    }
}
