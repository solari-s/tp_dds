package com.app.habitacion;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table (name = "estados")
public class HistorialEstadoHabitacion {
    
    @EmbeddedId
    private HistorialHabitacionPK id;

    @ManyToOne
    @MapsId("habitacionPK") // dice: esto forma parte de mi PK
    @JoinColumns({
        @JoinColumn(name = "nombre", referencedColumnName = "nombre"),
        @JoinColumn(name = "tipo", referencedColumnName = "tipo")
    })
    private Habitacion habitacion;
    
    @Column(nullable = false)
    private String horaInicio;
    
    @Column(nullable = false)
    private Date fechaFin;
    
    @Column(nullable = false)
    private String horaFin;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;

    public HistorialEstadoHabitacion(){}

    public HistorialEstadoHabitacion(HistorialEstadoHabitacionDTO h){
        id.setTipo(h.getTipo());
    }

    public HistorialEstadoHabitacion(Habitacion h, String horaInicio, Date fechaInicio, EstadoHabitacion estado){

        this.habitacion = h;
        id.setNumero(h.getNumero());
        id.setTipo(h.getTipo());
        this.horaInicio = horaInicio;
        id.setFecha(fechaInicio);
        this.estado = estado;
    }

    public HistorialEstadoHabitacion(Habitacion h, String horaInicio, Date fechaInicio,String horaFin, Date fechaFin, EstadoHabitacion estado){

        this.habitacion = h;
        id.setNumero(h.getNumero());
        id.setTipo(h.getTipo());
        this.horaInicio = horaInicio;
        id.setFecha(fechaInicio);
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    //getter
    public HistorialHabitacionPK getId() { return id; }
    public TipoHabitacion getTipo(){ return id.getTipo();}
    public int getNumero(){ return id.getNumero();}
    public Habitacion getHabitacion() { return habitacion; }
    public Date getFechaInicio() {return id.getFecha(); }
    public String getHoraInicio() { return horaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public String getHoraFin() { return horaFin; }
    public EstadoHabitacion getEstado() { return estado; }

    //setter
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    public void setTipo(TipoHabitacion tipo){  id.setTipo(tipo);}
    public void setNumero(int numero){  id.setNumero(numero);}
    public void setFechaInicio(Date fechaInicio) { id.setFecha(fechaInicio); }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    public void setEstado(EstadoHabitacion estado) { this.estado = estado; }

}
