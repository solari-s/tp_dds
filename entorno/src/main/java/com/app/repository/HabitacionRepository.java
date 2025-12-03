package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.habitacion.HabitacionPK;
import com.app.habitacion.TipoHabitacion;
import com.app.habitacion.Habitacion;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,HabitacionPK>{

    
    Optional<Habitacion> findByNumeroAndTipo(Integer numero, TipoHabitacion tipo);
}