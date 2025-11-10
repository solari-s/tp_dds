package com.app.responsablePago;

import com.app.huesped.Huesped;

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