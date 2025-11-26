package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.habitacion.HabitacionPK;
import com.app.habitacion.Habitacion;
public interface HabitacionRepository extends JpaRepository<Habitacion,HabitacionPK>{}