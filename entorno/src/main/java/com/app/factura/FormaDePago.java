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
    Banco tarjetaDeCredito;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    Banco tarjetaDeDebito;    

    @Column(nullable = true)
    boolean efectivo;

    public FormaDePago(){}

    //aca habria que hacer que solo uno sea != de null no?
    //ponemos el monto?
    public FormaDePago(Banco credito, Banco debito, boolean efectivo){
        
        tarjetaDeCredito = credito;
        tarjetaDeDebito = debito;
        this.efectivo = efectivo;

    }


}
