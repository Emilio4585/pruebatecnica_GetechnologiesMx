package com.GetechnologiesMx.pruebatecnica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GetechnologiesMx.pruebatecnica.model.Factura;
import com.GetechnologiesMx.pruebatecnica.model.Persona;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByPersona(Persona persona);
    void deleteByPersona(Persona persona);
}