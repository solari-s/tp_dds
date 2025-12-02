package com.app.estadias;

import java.util.Date;
import com.app.factura.*;
import com.app.habitacion.Habitacion;
import com.app.habitacion.Reserva;
import com.app.habitacion.TipoHabitacion;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "estadias")
public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "habitacion_numero", referencedColumnName = "numero"),
        @JoinColumn(name = "habitacion_tipo", referencedColumnName = "tipo")
    })
    private Habitacion habitacion;

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

    @OneToMany (mappedBy = "estadia")
    private List<Consumo> consumos;

    public void agregarConsumo(Consumo c){//NO CONSIDERO QUE ESTÉN EN DISTINTAS MONEDAS
        consumos.add(c);
    }

    public float totalConsumos(){
        float total = 0;
        for(Consumo c  : consumos ){
            total += c.getMonto();
        }
        return total; //NO CONSIDERO QUE ESTÉN EN DISTINTAS MONEDAS
    }


    //consutructores
    public Estadia(){}

    public Estadia(Reserva reserva, Date fechaInicio){
        this.reserva = reserva;
        this.fechaInicio = fechaInicio;
        consumos = new ArrayList<>();
    }

    public Estadia(Reserva reserva, Date fechaInicio, Date fechaFin){
        this.reserva = reserva;
        this.fechaInicio = fechaInicio;
        consumos = new ArrayList<>();
    }


    //getters
    public int getId() { return id; }
    public TipoHabitacion geTipoHabitacion(){ return habitacion.getTipo(); }
    public float getPrecio() { return precio; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public Factura getFactura() { return factura; }
    public Reserva getReserva() { return reserva; }

    //setters
    public void setPrecio(float precio) { this.precio = precio; }
    public void geTipoHabitacion(TipoHabitacion t){ habitacion.setTipo(t); }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

}
