package com.app.habitacion;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table (name = "estados")
public class HistorialEstadoHabitacion {
    
    @Id
    private Date FechaInicio;
    private String HoraInicio;
    private Date FechaFin;
    private String HoraFin;
    private EstadoHabitacion Estado;

    @ManyToOne
    @JoinColumn(name = "estado_habitacion")
    private Habitacion habitacion;


}
