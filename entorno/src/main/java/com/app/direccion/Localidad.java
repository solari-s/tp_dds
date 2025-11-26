package com.app.direccion;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "localidades")
public class Localidad {
    
    @Id
    private String nombre;

    // Muchas localidades pertenecen a una provincia
    @ManyToOne(optional = false)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;



}
