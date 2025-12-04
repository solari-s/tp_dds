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

import com.app.habitacion.EstadoHabitacion;
import com.app.habitacion.Habitacion;
import com.app.habitacion.HistorialEstadoHabitacion;
import com.app.habitacion.TipoHabitacion;
import com.app.repository.HistorialEstadoHabitacionRepository;

@Service
public class GestorReservas {

    @Autowired
    private HistorialEstadoHabitacionRepository historialRepo;

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

        // 2. Convertir fechas (del front vienen yyyy-MM-dd)
        Date desde = parsearFechaFront(desdeStr);
        Date hasta = parsearFechaFront(hastaStr);

        if (desde == null || hasta == null || desde.after(hasta)) {
            System.out.println("Rango de fechas inválido");
            return listaResultado;
        }

        // 3. Generar todas las fechas dentro del rango
        List<Date> rango = generarRangoFechas(desde, hasta);

        // 4. Iterar fechas y consultar disponibilidad
        for (Date fecha : rango) {
            boolean sd1 = verificarLibre(1, tipoEnum, fecha);
            boolean sd2 = verificarLibre(2, tipoEnum, fecha);

            Map<String, Object> fila = new HashMap<>();
            fila.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(fecha));
            fila.put("sd1", sd1);
            fila.put("sd2", sd2);

            listaResultado.add(fila);
        }

        return listaResultado;
    }

    // -------------------- UTILIDADES ------------------------ //

    public String crearReserva(String tipoStr, String fechaStr, int numeroHab) {
        try {
            // Convertir fecha
            Date fecha = parsearFechaFront(fechaStr);

            // Normalizar tipo
            TipoHabitacion tipoEnum = normalizarTipo(tipoStr);
            if (tipoEnum == null) {
                return "Tipo de habitación inválido";
            }

            // Validar disponibilidad
            if (!verificarLibre(numeroHab, tipoEnum, fecha)) {
                return "Error: La habitación ya está reservada u ocupada en esa fecha.";
            }

            // Crear referencia a la habitación
            Habitacion habitacionRef = new Habitacion();
            habitacionRef.setNumero(numeroHab);
            habitacionRef.setTipo(tipoEnum);

            // Crear la reserva
            HistorialEstadoHabitacion nuevo = new HistorialEstadoHabitacion(
                    habitacionRef,
                    "14:00",
                    fecha,
                    "10:00",
                    fecha,
                    EstadoHabitacion.Reservada);

            // Guardar
            historialRepo.save(nuevo);

            return "¡Reserva Exitosa!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el servidor: " + e.getMessage();
        }
    }

    private TipoHabitacion normalizarTipo(String t) {
        try {
            return TipoHabitacion.valueOf(
                    t.toUpperCase()
                            .replace("Á", "A")
                            .replace("É", "E")
                            .replace("Í", "I")
                            .replace("Ó", "O")
                            .replace("Ú", "U")
                            .replace(" ", "_"));
        } catch (Exception e) {
            return null;
        }
    }

    private Date parsearFechaFront(String f) {
        try {
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

    private boolean verificarLibre(int numero, TipoHabitacion tipo, Date fecha) {
        List<HistorialEstadoHabitacion> historial = historialRepo.findByHabitacion(numero, tipo);

        for (HistorialEstadoHabitacion h : historial) {
            if (mismoDia(h.getFechaInicio(), fecha)) {
                if (h.getEstado() == EstadoHabitacion.Ocupada ||
                        h.getEstado() == EstadoHabitacion.Reservada) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean mismoDia(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }
}
