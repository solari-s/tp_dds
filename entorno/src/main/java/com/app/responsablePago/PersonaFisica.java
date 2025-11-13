package com.app.responsablePago;

import com.app.huesped.Huesped;

import jakarta.persistence.*;

@Entity
@Table(name = "personas_fisicas")
public class PersonaFisica extends ResponsablePago {    

    @Column(nullable = false, length = 100) //debe resolverse que PosicionIVA default es Consumidor final
    private String PosicionIVA;
    
    @OneToOne
    private Huesped RefHuesped;

    protected PersonaFisica() {}

    public PersonaFisica(String PosicionIVA, String CUIT, Huesped RefHuesped) {
        super(CUIT);
        this.PosicionIVA = PosicionIVA;
        this.RefHuesped = RefHuesped;
    }

    public String getPosicionIVA() {
        return PosicionIVA;
    }

    public Huesped getHuesped() {
        return RefHuesped;
    }

}