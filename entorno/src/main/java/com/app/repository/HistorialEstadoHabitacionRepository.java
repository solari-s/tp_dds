package com.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.HistorialHabitacionPK;
import com.app.habitacion.TipoHabitacion;

public interface HistorialEstadoHabitacionRepository
    extends JpaRepository<HistorialEstadoHabitacion, HistorialHabitacionPK> {

  @Query("SELECT h FROM HistorialEstadoHabitacion h " +
      "WHERE h.id.numero = :numero AND h.id.tipo = :tipo")
  List<HistorialEstadoHabitacion> findByHabitacion(
      @Param("numero") int numero,
      @Param("tipo") TipoHabitacion tipo);

  @Query("""
      SELECT h FROM HistorialEstadoHabitacion h
      WHERE h.id.numero = :numero
        AND h.id.tipo = :tipo
        AND (
              (h.id.fecha BETWEEN :inicio AND :fin)
              OR
              (h.fechaFin IS NOT NULL AND h.fechaFin BETWEEN :inicio AND :fin)
              OR
              (h.id.fecha <= :inicio AND h.fechaFin >= :fin)
            )
      """)
  List<HistorialEstadoHabitacion> buscarEstadosEnRango(
      @Param("numero") int numero,
      @Param("tipo") TipoHabitacion tipo,
      @Param("inicio") Date inicio,
      @Param("fin") Date fin);
}
