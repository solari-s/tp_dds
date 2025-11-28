package com.app.habitacion;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity(name = "reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String apellido;
    private int telefono;
    private Date fechaIngreso;
    private String HoraIngreso;
    private Date fechaEgreso;
    private String HoraEgreso;

    @ManyToMany
    @JoinTable(name = "habitaciones_reservadas", 
    joinColumns = @JoinColumn(name = "reserva_id"), 
    inverseJoinColumns = @JoinColumn(name = "habitacion_id"))
    private List<Habitacion> habitacionesReservadas;

    @OneToOne(mappedBy = "reserva")
    private Estadia estadia;
    
    //getters y setters
}
