package com.app.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.HistorialHabitacionPK;
import com.app.habitacion.TipoHabitacion;

@Repository
public interface HistorialEstadoHabitacionRepository
        extends JpaRepository<HistorialEstadoHabitacion, HistorialHabitacionPK> {

    // Buscar historial de una habitación específica
    @Query("SELECT h FROM HistorialEstadoHabitacion h WHERE h.id.habitacionPK.numero = :numero AND h.id.habitacionPK.tipo = :tipo")
    List<HistorialEstadoHabitacion> findByHabitacion(@Param("numero") int numero, @Param("tipo") TipoHabitacion tipo);
}