package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.factura.Factura;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,Integer>{}
