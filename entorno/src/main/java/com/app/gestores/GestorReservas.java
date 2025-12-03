package com.app.gestores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Método que llama el Controller.
     * Retorna una lista de Mapas para que el HTML la entienda fácil.
     */
    public List<Map<String, Object>> buscarDisponibilidad(String tipoString) {
        List<Map<String, Object>> listaResultado = new ArrayList<>();

        // 1. Convertir String (del HTML) a Enum (Java)
        TipoHabitacion tipoEnum;
        try {
            // Ejemplo: "Individual estándar" -> INDIVIDUAL_ESTANDAR
            tipoEnum = TipoHabitacion.valueOf(tipoString.toUpperCase().replace(" ", "_").replace("Á", "A"));
        } catch (Exception e) {
            System.out.println("Error convirtiendo tipo: " + tipoString);
            return listaResultado;
        }

        // 2. Definir fechas a consultar (Simulamos 3 días para el ejemplo)
        String[] fechasStr = { "10/04/25", "11/04/25", "12/04/25" };

        // 3. Iterar fechas y consultar disponibilidad
        for (String fecha : fechasStr) {
            Date fechaDate = parsearFecha(fecha);

            // Asumimos que "sd1" es habitación #1 y "sd2" es habitación #2
            boolean sd1 = verificarLibre(1, tipoEnum, fechaDate);
            boolean sd2 = verificarLibre(2, tipoEnum, fechaDate);

            // 4. Crear el MAPA (La solución al error de tu imagen)
            Map<String, Object> fila = new HashMap<>();
            fila.put("fecha", fecha); // String fecha
            fila.put("sd1", sd1); // boolean
            fila.put("sd2", sd2); // boolean

            listaResultado.add(fila);
        }

        return listaResultado;
    }

    // Método para guardar la reserva en la BDD
    public String crearReserva(String tipoStr, String fechaStr, int numeroHab) {
        try {
            // Conversión de datos
            Date fecha = parsearFecha(fechaStr);
            TipoHabitacion tipoEnum = TipoHabitacion.valueOf(tipoStr.toUpperCase().replace(" ", "_").replace("Á", "A"));

            // 1. Validar si ya está ocupada
            if (!verificarLibre(numeroHab, tipoEnum, fecha)) {
                return "Error: La habitación ya está ocupada en esa fecha.";
            }

            // 2. Crear el objeto HistorialEstadoHabitacion
            // Nota: Aquí necesitarías crear una instancia de 'Habitacion' con ese ID
            // compuesto para asignarla
            // Como no tenemos el Repo de Habitacion instanciado, creamos el objeto clave
            // manual:

            // (Para este ejemplo rápido asumimos que la entidad Habitacion existe en BDD)
            Habitacion habitacionRef = new Habitacion();
            habitacionRef.setNumero(numeroHab);
            habitacionRef.setTipo(tipoEnum);

            HistorialEstadoHabitacion nuevoEstado = new HistorialEstadoHabitacion(
                    habitacionRef,
                    "14:00", // Hora check-in default
                    fecha, // Fecha inicio
                    "10:00", // Hora fin default
                    fecha, // Fecha fin (mismo día para prueba)
                    EstadoHabitacion.Reservada);

            // 3. Guardar
            historialRepo.save(nuevoEstado);
            return "¡Reserva Exitosa!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el servidor: " + e.getMessage();
        }
    }

    // Método auxiliar para verificar si una habitación está libre
    private boolean verificarLibre(int numero, TipoHabitacion tipo, Date fecha) {
        List<HistorialEstadoHabitacion> historial = historialRepo.findByHabitacion(numero, tipo);

        for (HistorialEstadoHabitacion h : historial) {
            // Si coincide la fecha y NO está disponible...
            if (mismoDia(h.getFechaInicio(), fecha)) {
                if (h.getEstado() == EstadoHabitacion.Ocupada || h.getEstado() == EstadoHabitacion.Reservada) {
                    return false; // Está ocupada
                }
            }
        }
        return true; // Está libre
    }

    // Utilidades
    private Date parsearFecha(String f) {
        try {
            return new SimpleDateFormat("dd/MM/yy").parse(f);
        } catch (Exception e) {
            return new Date();
        }
    }

    private boolean mismoDia(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }
}