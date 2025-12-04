package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.responsablePago.PersonaJuridica;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, String> {
}
