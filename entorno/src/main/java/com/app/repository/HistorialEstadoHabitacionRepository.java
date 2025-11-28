package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.habitacion.HistorialEstadoHabitacion;
import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialEstadoHabitacionRepository extends JpaRepository<HistorialEstadoHabitacion,Date>{}