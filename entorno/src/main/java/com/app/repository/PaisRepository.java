package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.direccion.Pais;
import org.springframework.stereotype.Repository;

@Repository

public interface PaisRepository extends JpaRepository<Pais,String> {}
