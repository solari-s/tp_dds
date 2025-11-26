package com.app.habitacion;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

@Embeddable
public class HabitacionPK implements Serializable {

    @Column(nullable = false)
    private TipoHabitacion tipo;

    @Column(nullable = false)
    private int numero;

    // equals() y hashCode() obligatorios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitacionPK)) return false;
        HabitacionPK that = (HabitacionPK) o;
       return Objects.equals(numero, that.numero) && Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, numero);
    }

}
