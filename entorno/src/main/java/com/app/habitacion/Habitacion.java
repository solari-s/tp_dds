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
}
