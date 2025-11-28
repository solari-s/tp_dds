package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.responsablePago.ResponsablePago;

import org.springframework.stereotype.Repository;

@Repository
public interface ResponsablePagoRepository extends JpaRepository<ResponsablePago, String> {
    //los métodos CRUD ya están implementados por JpaRepository
}
