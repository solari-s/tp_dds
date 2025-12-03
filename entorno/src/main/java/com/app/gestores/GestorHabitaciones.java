package com.app.gestores;
import org.springframework.transaction.annotation.Transactional;

import com.app.habitacion.EstadoHabitacion;
import com.app.habitacion.Habitacion;
import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.TipoHabitacion;
import com.app.repository.HistorialEstadoHabitacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorHabitaciones {
    @org.springframework.beans.factory.annotation.Autowired
    private com.app.repository.HabitacionRepository habitacionRepository;

    @Transactional(readOnly = true)
    public java.util.List<com.app.habitacion.HabitacionDTO> mostrarEstadoHabitaciones(java.util.Date fechaInicio, java.util.Date fechaFin) {
        java.util.List<com.app.habitacion.Habitacion> habitaciones = habitacionRepository.findAll();
        java.util.List<com.app.habitacion.HabitacionDTO> dtos = new java.util.ArrayList<>();
    
        // Generamos la lista de días entre fechaInicio y fechaFin
        java.util.List<java.util.Date> rangoFechas = generarRangoFechas(fechaInicio, fechaFin);

        for (com.app.habitacion.Habitacion h : habitaciones) {
            com.app.habitacion.HabitacionDTO dto = new com.app.habitacion.HabitacionDTO();
            dto.setNumero(h.getNumero());
            dto.setTipo(h.getTipo());
            dto.setCostoNoche(h.getCostoNoche());
        
            java.util.List<com.app.habitacion.EstadoHabitacion> estados = new java.util.ArrayList<>();
            
            // El Gestor pide a la habitación su estado por cada día (Secuencia UI -> Gestor -> Habitacion)
            for (java.util.Date fecha : rangoFechas) {
                estados.add(h.getEstadoEnFecha(fecha));
            }
            
            dto.setEstadosPorDia(estados);
            dtos.add(dto);
        }
        return dtos;
    }

    @Autowired
    private HistorialEstadoHabitacionRepository historialRepository;

    @Transactional
    public void ocuparHabitacion(int numero, TipoHabitacion tipo,
                                 java.util.Date fechaInicio, String horaInicio,
                                 java.util.Date fechaFin, String horaFin) {

        // Buscar la habitación
        Habitacion habitacion = habitacionRepository
                .findByNumeroAndTipo(numero, tipo)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        // Crear historial
        HistorialEstadoHabitacion historial =
                new HistorialEstadoHabitacion(
                        habitacion,
                        horaInicio,
                        fechaInicio,
                        horaFin,
                        fechaFin,
                        EstadoHabitacion.Ocupada
                );

        historialRepository.save(historial);
    }

    @Transactional
    public void reservarHabitacion(int numero, TipoHabitacion tipo,
                                 java.util.Date fechaInicio, String horaInicio,
                                 java.util.Date fechaFin, String horaFin) {

        // Buscar la habitación
        Habitacion habitacion = habitacionRepository
                .findByNumeroAndTipo(numero, tipo)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        // Crear historial
        HistorialEstadoHabitacion historial =
                new HistorialEstadoHabitacion(
                        habitacion,
                        horaInicio,
                        fechaInicio,
                        horaFin,
                        fechaFin,
                        EstadoHabitacion.Reservada
                );

        historialRepository.save(historial);
    }

    // Método auxiliar para generar fechas consecutivas
    private java.util.List<java.util.Date> generarRangoFechas(java.util.Date start, java.util.Date end) {
        java.util.List<java.util.Date> dates = new java.util.ArrayList<>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            dates.add(calendar.getTime());
            calendar.add(java.util.Calendar.DATE, 1);
        }
        return dates;
    }
}
