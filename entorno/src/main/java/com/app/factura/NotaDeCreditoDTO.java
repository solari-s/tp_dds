package com.app.factura;

import java.util.Date;

public class NotaDeCreditoDTO {
    
    private Date fechaEmision;
    private float monto;
    private Factura facturaAsociada;

    //constructores
    public NotaDeCreditoDTO(){}   
    public NotaDeCreditoDTO(Date fechaEmision, float monto, Factura facturaAsociada){
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.facturaAsociada = facturaAsociada;
    }

    //getters y setters
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }
    public Factura getFacturaAsociada() { return facturaAsociada; }
    public void setFacturaAsociada(Factura facturaAsociada) { this.facturaAsociada = facturaAsociada; }
}