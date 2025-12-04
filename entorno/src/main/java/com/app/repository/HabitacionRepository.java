package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.habitacion.Habitacion;
import com.app.habitacion.TipoHabitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    Habitacion findByIdNumeroAndIdTipo(int numero, TipoHabitacion tipo);

    default Habitacion findByNumeroAndTipo(int numero, TipoHabitacion tipo) {
        return findByIdNumeroAndIdTipo(numero, tipo);
    }
}
