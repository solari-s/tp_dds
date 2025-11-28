package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.habitacion.Estadia;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Integer> {}