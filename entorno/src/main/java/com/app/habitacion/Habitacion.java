package com.app.habitacion;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity@Table (name = "habitaciones")
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
