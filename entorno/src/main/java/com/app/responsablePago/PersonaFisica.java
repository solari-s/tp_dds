package com.app.responsablePago;

import com.app.huesped.Huesped;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class PersonaFisica extends ResponsablePago {

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