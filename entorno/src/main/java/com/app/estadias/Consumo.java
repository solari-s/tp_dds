package com.app.estadias;

public class Consumo {
    
    private TipoConsumo tipo;
    private float monto;
    private Moneda moneda;

    //consutructores
    public Consumo(){}
    public Consumo(TipoConsumo tipo, float monto, Moneda moneda){
        this.tipo = tipo;
        this.monto = monto;
        this.moneda = moneda;
    }

    //getter
    public TipoConsumo getTipo() { return tipo; }
    public float getMonto() { return monto; }
    public Moneda getMoneda() { return moneda; }

    //setter
    public void setTipo(TipoConsumo tipo) { this.tipo = tipo; }
    public void setMonto(float monto) { this.monto = monto; }
    public void setMoneda(Moneda moneda) { this.moneda = moneda; }

}
