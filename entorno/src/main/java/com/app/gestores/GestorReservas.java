package com.app.gestores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para guardar

import com.app.habitacion.EstadoHabitacion;
import com.app.habitacion.Habitacion;
import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.TipoHabitacion;
import com.app.repository.HabitacionRepository;
import com.app.repository.HistorialEstadoHabitacionRepository;

@Service
public class GestorReservas {

    @Autowired
    private HistorialEstadoHabitacionRepository historialRepo;
    private HabitacionRepository habitacionRepo;

    /**
     * Busca disponibilidad por rango de fechas y tipo de habitación.
     */
    public List<Map<String, Object>> buscarDisponibilidad(String tipoString, String desdeStr, String hastaStr) {

        List<Map<String, Object>> listaResultado = new ArrayList<>();

        // 1. Convertir tipo a ENUM
        TipoHabitacion tipoEnum = normalizarTipo(tipoString);
        if (tipoEnum == null) {
            System.out.println("Tipo inválido recibido: " + tipoString);
            return listaResultado;
        }

        // 2. Convertir fechas
        Date desde = parsearFechaFront(desdeStr);
        Date hasta = parsearFechaFront(hastaStr);

        if (desde == null || hasta == null || desde.after(hasta)) {
            return listaResultado;
        }

        // 3. Generar todas las fechas dentro del rango
        List<Date> rango = generarRangoFechas(desde, hasta);

        // 4. Iterar fechas y consultar disponibilidad
        for (Date fecha : rango) {
            boolean sd1 = verificarLibre(1, tipoEnum, fecha);
            boolean sd2 = verificarLibre(2, tipoEnum, fecha);
            // (Si tienes más habitaciones, deberías iterarlas dinámicamente,
            // pero mantenemos tu lógica actual de 2 habitaciones por tipo)

            Map<String, Object> fila = new HashMap<>();
            fila.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(fecha));
            fila.put("sd1", sd1);
            fila.put("sd2", sd2);

            listaResultado.add(fila);
        }

        return listaResultado;
    }

    // ---------------------------------------------------------------
    // MÉTODO CORREGIDO: Ahora acepta RANGO (Fecha Inicio y Fin)
    // ---------------------------------------------------------------
    @Transactional
    public String crearReserva(String tipoStr, int numeroHab, String fechaInicioStr, String fechaFinStr) {
        try {
            // 1. Parsear Fechas
            Date fechaInicio = parsearFechaFront(fechaInicioStr);
            Date fechaFin = parsearFechaFront(fechaFinStr);

            if (fechaInicio == null || fechaFin == null) {
                return "Error: Formato de fechas incorrecto.";
            }

            // 2. Normalizar tipo
            TipoHabitacion tipoEnum = normalizarTipo(tipoStr);
            if (tipoEnum == null) {
                return "Error: Tipo de habitación inválido";
            }

            // 3. Validar disponibilidad para TODOS los días del rango
            // Si un solo día del rango está ocupado, no se puede reservar.
            List<Date> diasSolicitados = generarRangoFechas(fechaInicio, fechaFin);

            for (Date dia : diasSolicitados) {
                if (!verificarLibre(numeroHab, tipoEnum, dia)) {
                    String diaOcupado = new SimpleDateFormat("dd/MM/yyyy").format(dia);
                    return "Error: La habitación ya está ocupada el día " + diaOcupado;
                }
            }

            Habitacion habitacionRef = habitacionRepo.findByNumeroAndTipo(numeroHab, tipoEnum);
            if (habitacionRef == null) {
                return "Error: La habitación no existe en la base de datos.";
            }

            // 5. Crear la reserva (Un solo registro con fecha inicio y fin)
            HistorialEstadoHabitacion nuevo = new HistorialEstadoHabitacion(
                    habitacionRef,
                    "14:00", // Hora Check-in default
                    fechaInicio,
                    "10:00", // Hora Check-out default
                    fechaFin,
                    EstadoHabitacion.Reservada);

            // 6. Guardar
            historialRepo.save(nuevo);

            return "¡Reserva Exitosa para la habitación " + numeroHab + "!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el servidor: " + e.getMessage();
        }
    }

    // -------------------- UTILIDADES ------------------------ //

    private TipoHabitacion normalizarTipo(String t) {
        try {
            // Intenta coincidencia exacta primero
            return TipoHabitacion.valueOf(t);
        } catch (Exception e) {
            // Si falla, intenta limpiar strings sucios
            try {
                return TipoHabitacion.valueOf(
                        t.trim().toUpperCase()
                                .replace("Á", "A")
                                .replace("É", "E")
                                .replace("Í", "I")
                                .replace("Ó", "O")
                                .replace("Ú", "U")
                                .replace(" ", "_"));
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private Date parsearFechaFront(String f) {
        try {
            // El front manda yyyy-MM-dd
            return new SimpleDateFormat("yyyy-MM-dd").parse(f);
        } catch (Exception e) {
            return null;
        }
    }

    private List<Date> generarRangoFechas(Date d1, Date d2) {
        List<Date> lista = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);

        while (!cal.getTime().after(d2)) {
            lista.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return lista;
    }

    private boolean verificarLibre(int numero, TipoHabitacion tipo, Date fechaConsulta) {
        // Traemos todo el historial de esa habitación
        List<HistorialEstadoHabitacion> historial = historialRepo.findByHabitacion(numero, tipo);

        // Revisamos si la fecha consultada cae dentro de algún rango reservado/ocupado
        for (HistorialEstadoHabitacion h : historial) {

            // Si el estado es Disponible, no nos importa, seguimos buscando bloqueos
            if (h.getEstado() == EstadoHabitacion.Disponible)
                continue;

            // Chequeo de rango: ¿La fechaConsulta está entre h.inicio y h.fin?
            if (!fechaConsulta.before(h.getFechaInicio()) && !fechaConsulta.after(h.getFechaFin())) {
                return false; // Está ocupada
            }
        }
        return true; // Está libre
    }
}