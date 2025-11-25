package com.app.direccion;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "localidades")
public class Localidad {
    
    @Id
    private String nombre;

    @OneToMany(mappedBy = "localidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Provincia> provincias;



}
