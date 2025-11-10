package com.app.responsablePago;

public abstract class ResponsablePago {
    private String CUIT;

    public ResponsablePago(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getCUIT() {
        return CUIT;
    }

}