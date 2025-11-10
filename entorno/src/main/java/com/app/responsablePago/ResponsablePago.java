package com.app.responsablePago;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class ResponsablePago {
    private String CUIT;

    public ResponsablePago(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getCUIT() {
        return CUIT;
    }

}