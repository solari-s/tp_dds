package com.app.factura;

import jakarta.persistence.*;

public class FormaDePago {
    
    @Enumerated(EnumType.STRING)
    Banco tarjetaDeCredito;
    
    @Enumerated(EnumType.STRING)
    Banco tarjetaDeDebito;    

    boolean efectivo;




}
