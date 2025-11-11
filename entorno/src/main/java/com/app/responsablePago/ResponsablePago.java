package com.app.responsablePago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ResponsablePago {
    
    @Id
    @Column(nullable = false, unique = true)
    private String CUIT;

    protected ResponsablePago() {}

    public ResponsablePago(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getCUIT() {
        return CUIT;
    }

}