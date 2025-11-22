package com.app.responsablePago;

import jakarta.persistence.*;

@Entity
@Table(name = "personas_juridicas")
public class PersonaJuridica extends ResponsablePago {
    
    @Column(nullable = false, length = 100) 
    private String RazonSocial;

    //siempre si es tercero es pj entonces no tiene huesped asociado?

}
