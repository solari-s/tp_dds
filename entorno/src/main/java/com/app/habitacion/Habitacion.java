package com.app.habitacion;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table (name = "habitaciones")
public class Habitacion {

    @EmbeddedId
    private HabitacionPK id;

    @Column (nullable = false)
    private float costoNoche;
    
    @OneToMany(mappedBy = "habitacion")
    private List<HistorialEstadoHabitacion> historialEstados;

    @ManyToMany(mappedBy = "habitacionesReservadas")
    private List<Reserva> reservas;

    //constructores
    public Habitacion(){}

    public Habitacion(HabitacionDTO h){
        id.setTipo(h.getTipo());
        id.setNumero(h.getNumero());
        this.costoNoche = h.getCostoNoche();
        this.historialEstados = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
    public Habitacion(String tipo, int numero,float costoNoche){
        id.setTipo(TipoHabitacion.fromString(tipo)); 
        id.setNumero(numero);
        this.costoNoche = costoNoche;
        historialEstados = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public Habitacion(TipoHabitacion tipo, int numero,float costoNoche){
        id.setTipo(tipo); 
        id.setNumero(numero);
        this.costoNoche = costoNoche;
        historialEstados = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public EstadoHabitacion getEstadoEnFecha(java.util.Date fecha) {
        if (this.historialEstados == null || this.historialEstados.isEmpty()) {
            return EstadoHabitacion.Disponible;
        }

        // 1. Normalizar la fecha consultada (poner hora a 00:00:00)
        java.util.Calendar calConsulta = java.util.Calendar.getInstance();
        calConsulta.setTime(fecha);
        resetTime(calConsulta);
        java.util.Date fechaConsultaZero = calConsulta.getTime();

        for (HistorialEstadoHabitacion historial : this.historialEstados) {
            // 2. Normalizar fecha inicio del historial
            java.util.Calendar calInicio = java.util.Calendar.getInstance();
            calInicio.setTime(historial.getFechaInicio());
            resetTime(calInicio);

            // 3. Normalizar fecha fin del historial
            java.util.Calendar calFin = java.util.Calendar.getInstance();
            calFin.setTime(historial.getFechaFin());
            resetTime(calFin);

            // 4. Comparar: (fecha >= inicio) Y (fecha <= fin)
            // !before(inicio) equivale a >= inicio
            // !after(fin) equivale a <= fin
            if (!fechaConsultaZero.before(calInicio.getTime()) && !fechaConsultaZero.after(calFin.getTime())) {
                return historial.getEstado();
            }
        }
        return EstadoHabitacion.Disponible;
    }

    // Método auxiliar privado para poner la hora en cero
    private void resetTime(java.util.Calendar cal) {
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
    }

    //getters
    public TipoHabitacion getTipo() { return id.getTipo(); }
    public int getNumero() { return id.getNumero(); }
    public float getCostoNoche() { return costoNoche; }
    public List<HistorialEstadoHabitacion> getHistorialEstados() { return historialEstados; }
    public List<Reserva> getReservas() { return reservas; }

    //setters
    public void setTipo(TipoHabitacion tipo) { id.setTipo(tipo); }
    public void setNumero(int numero) { id.setNumero(numero); }    
    public void setCostoNoche(float costoNoche) { this.costoNoche = costoNoche; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
}
