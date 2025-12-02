package com.app.factura;

import java.util.Date;

public class NotaDeCredito {
    
    private Date fechaEmision;
    private float monto;
    private Factura facturaAsociada;

    //constructores
    public NotaDeCredito(){}   
    public NotaDeCredito(Date fechaEmision, float monto, Factura facturaAsociada){
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.facturaAsociada = facturaAsociada;
    }

    public NotaDeCredito(NotaDeCreditoDTO n){
        this.fechaEmision = n.getFechaEmision();
        this.monto = n.getMonto();
        this.facturaAsociada = n.getFacturaAsociada();
    }

    //getters y setters
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }
    public Factura getFacturaAsociada() { return facturaAsociada; }
    public void setFacturaAsociada(Factura facturaAsociada) { this.facturaAsociada = facturaAsociada; }
}