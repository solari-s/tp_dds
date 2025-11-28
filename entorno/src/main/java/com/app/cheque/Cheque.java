package com.app.cheque;

import jakarta.persistence.*;
import com.app.factura.Banco;
import java.util.Date;

@Entity
@Table(name = "cheques")
public class Cheque {
     
    @Id
    private int id;

    private int nro;
    private Banco banco;
    private String plaza;
    private float monto;
    private Date fechaCobro;
}
