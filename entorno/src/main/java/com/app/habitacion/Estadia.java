package com.app.habitacion;

import java.util.Date;
import com.app.factura.*;
import jakarta.persistence.*;

@Entity
@Table(name = "estadias")
public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipo;

    @Column(nullable = false)
    private float precio;
    
    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @OneToOne
    private Factura factura;

    @OneToOne(optional = true)
    @JoinColumn(name = "reserva_id", nullable = true)
    private Reserva reserva;

    public Estadia(){}

    //getters
    public int getId() { return id; }
    public TipoHabitacion getTipo() { return tipo; }
    public float getPrecio() { return precio; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public Factura getFactura() { return factura; }
    public Reserva getReserva() { return reserva; }

    //setters
    public void setTipo(TipoHabitacion tipo) { this.tipo = tipo; }
    public void setPrecio(float precio) { this.precio = precio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

}
