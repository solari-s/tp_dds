package com.app.factura;

import jakarta.persistence.*;

@Entity
@Table(name = "formas_de_pago")
public class FormaDePago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Banco tarjetaDeCredito;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Banco tarjetaDeDebito;    

    @Column(nullable = true)
    private boolean efectivo;

    @Column(nullable = false)
    private float monto;

    @ManyToOne
    @JoinColumn(name = "formas_de_pago")
    private Factura factura;

    public FormaDePago(){}

    public FormaDePago(Banco credito, Banco debito, float monto){
        
        tarjetaDeCredito = credito;
        tarjetaDeDebito = debito;
        this.efectivo = false;
        this.monto = monto;

    }

    public FormaDePago(boolean efectivo,float monto){
        
        tarjetaDeCredito =null;
        tarjetaDeDebito = null;
        this.efectivo = efectivo;
        this.monto = monto;

    }    

}
