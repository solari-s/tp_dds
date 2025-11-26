package com.app.habitacion;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class Reserva {
    
    @OneToOne(mappedBy = "reserva")
    private Estadia estadia;

}
