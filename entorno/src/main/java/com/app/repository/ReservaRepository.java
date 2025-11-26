package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.habitacion.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Integer>{}
