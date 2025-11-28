package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.factura.FormaDePago;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago,Integer>{}
