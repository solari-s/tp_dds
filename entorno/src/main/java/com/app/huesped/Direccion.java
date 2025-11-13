package com.app.huesped;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    private int codigoPostal;
    
    @Column(nullable = false, length = 100)
    private String calle;

    @Column(nullable = false, length = 100)
    private String departamento;

    @Column(nullable = true, length = 100)
    private int altura;

    @Column(nullable = true, length = 100)
    private int piso;


    public Direccion(String calle, String departamento, int altura, int piso, int codigoPostal) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
    }
    //Getters
    public String getCalle() { return calle; }
    public String getDepartamento() { return departamento; }
    public int getAltura() { return altura; }
    public int getPiso() { return piso; }
    public int getCodigoPostal() { return codigoPostal; }
}
