package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.responsablePago.PersonaFisica;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, String> {
}
