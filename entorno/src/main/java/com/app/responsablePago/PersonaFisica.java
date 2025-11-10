package com.app.responsablePago;

import com.app.huesped.Huesped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonaFisica extends ResponsablePago {

    private String PosicionIVA;
    private Huesped RefHuesped;

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