package com.app.gestores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.habitacion.EstadoHabitacion;
import com.app.habitacion.Habitacion;
import com.app.habitacion.HabitacionDTO;
import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.HistorialHabitacionPK;
import com.app.habitacion.TipoHabitacion;
import com.app.repository.HabitacionRepository;
import com.app.repository.HistorialEstadoHabitacionRepository;

@Service
public class GestorHabitaciones {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HistorialEstadoHabitacionRepository historialRepository;

    // --------------------------------------------------------
    // 1) MOSTRAR ESTADO DE HABITACIONES ENTRE FECHAS
    // --------------------------------------------------------
    @Transactional(readOnly = true)
    public List<HabitacionDTO> mostrarEstadoHabitaciones(Date fechaInicio, Date fechaFin) {

        List<Habitacion> habitaciones = habitacionRepository.findAll();
        List<HabitacionDTO> dtos = new ArrayList<>();

        List<Date> rangoFechas = generarRangoFechas(fechaInicio, fechaFin);

        for (Habitacion h : habitaciones) {

            HabitacionDTO dto = new HabitacionDTO();
            dto.setNumero(h.getNumero());
            dto.setTipo(h.getTipo());
            dto.setCostoNoche(h.getCostoNoche());

            List<EstadoHabitacion> estados = new ArrayList<>();

            for (Date fecha : rangoFechas) {
                estados.add(h.getEstadoEnFecha(fecha));
            }

            dto.setEstadosPorDia(estados);
            dtos.add(dto);
        }
        return dtos;
    }

    // --------------------------------------------------------
    // 2) OCUPAR HABITACIÓN
    // --------------------------------------------------------
    @Transactional
    public void ocuparHabitacion(int numero, TipoHabitacion tipo,
            Date fechaInicio, String horaInicio,
            Date fechaFin, String horaFin) {

        Habitacion habitacion = habitacionRepository.findByIdNumeroAndIdTipo(numero, tipo);

        if (habitacion == null) {
            throw new RuntimeException("Habitación no encontrada");
        }

        HistorialEstadoHabitacion historial = new HistorialEstadoHabitacion(
                habitacion,
                horaInicio,
                fechaInicio,
                horaFin,
                fechaFin,
                EstadoHabitacion.Ocupada);

        historialRepository.save(historial);
    }

    // --------------------------------------------------------
    // 3) RESERVAR HABITACIÓN
    // --------------------------------------------------------
    @Transactional
    public void reservarHabitacion(int numero, TipoHabitacion tipo,
            Date fechaInicio, String horaInicio,
            Date fechaFin, String horaFin) {

        Habitacion habitacion = habitacionRepository.findByIdNumeroAndIdTipo(numero, tipo);

        if (habitacion == null) {
            throw new RuntimeException("Habitación no encontrada");
        }

        HistorialEstadoHabitacion historial = new HistorialEstadoHabitacion(
                habitacion,
                horaInicio,
                fechaInicio,
                horaFin,
                fechaFin,
                EstadoHabitacion.Reservada);

        historialRepository.save(historial);
    }

    // --------------------------------------------------------
    // 4) VERIFICAR DISPONIBILIDAD ENTRE FECHAS
    // --------------------------------------------------------
    @Transactional(readOnly = true)
    public EstadoHabitacion verificarDisponibilidad(
            HistorialHabitacionPK id,
            Date fechaInicio,
            Date fechaFin) {

        List<HistorialEstadoHabitacion> historial = historialRepository.buscarEstadosEnRango(id.getNumero(),
                id.getTipo(), fechaInicio, fechaFin);

        if (historial == null || historial.isEmpty()) {
            return EstadoHabitacion.Disponible;
        }

        for (HistorialEstadoHabitacion h : historial) {
            if (h.getEstado() == EstadoHabitacion.Ocupada) {
                return EstadoHabitacion.Ocupada;
            }
            if (h.getEstado() == EstadoHabitacion.Reservada) {
                return EstadoHabitacion.Reservada;
            }
        }
        return EstadoHabitacion.Disponible;
    }

    // --------------------------------------------------------
    // 5) GENERAR LISTA DE FECHAS ENTRE INICIO–FIN
    // --------------------------------------------------------
    private List<Date> generarRangoFechas(Date start, Date end) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (!calendar.getTime().after(end)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }
}
