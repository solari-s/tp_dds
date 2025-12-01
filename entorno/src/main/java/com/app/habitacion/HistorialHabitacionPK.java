package com.app.habitacion;

import jakarta.persistence.*;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HistorialHabitacionPK implements Serializable {

    @Embedded
    private HabitacionPK habitacionPK;

    @Column(nullable = false)
    private Date fecha;//inicio del estado  

    //hashcode y equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistorialHabitacionPK)) return false;
        HistorialHabitacionPK that = (HistorialHabitacionPK) o;
       return Objects.equals(habitacionPK, that.habitacionPK) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacionPK, fecha);
    }

    //constructores
    public HistorialHabitacionPK(){}

    public HistorialHabitacionPK(TipoHabitacion tipo, int numero, Date fecha){
        habitacionPK.setTipo(tipo); 
        habitacionPK.setNumero(numero);
        this.fecha = fecha;
    }

    
    //getter
    public TipoHabitacion getTipo() { return habitacionPK.getTipo(); }
    public int getNumero() { return habitacionPK.getNumero();  }
    public Date getFecha(){ return fecha;}

    //setter
    public void setTipo(TipoHabitacion tipo) { habitacionPK.setTipo(tipo); }
    public void setNumero(int numero) { habitacionPK.setNumero(numero); }
    public void setFecha(Date fecha){this.fecha = fecha;}
}