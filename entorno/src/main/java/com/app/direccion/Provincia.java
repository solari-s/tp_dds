package com.app.direccion;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

public class Provincia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Localidad> localidades;

    @ManyToOne
    @JoinColumn(name = "pais_nombre") 
    private Pais pais;

    public Provincia(){}

    public Provincia(String nombre, Pais pais){
        this.nombre = nombre;
        this.pais = pais;
        localidades = new ArrayList<>();
    }

    public String getNombre(){ return nombre; }

}
