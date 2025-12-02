package com.app.responsablePago;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ResponsablePago {
    
    @Id
    private String CUIT;

    protected ResponsablePago() {}

    public ResponsablePago(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getCUIT() {
        return CUIT;
    }

}