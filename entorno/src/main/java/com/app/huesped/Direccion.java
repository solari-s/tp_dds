package com.app.huesped;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Direccion {

    private String calle;
    private String departamento;
    private int altura;
    private int piso;
    @Id
    private int codigoPostal;
    private Huesped RefHuesped;

    protected Direccion() {}

    public Direccion(String calle, String departamento, int altura, int piso, int codigoPostal, Huesped RefHuesped) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
        this.RefHuesped = RefHuesped;
    }
    //Getters
    public String getCalle() { return calle; }
    public String getDepartamento() { return departamento; }
    public int getAltura() { return altura; }
    public int getPiso() { return piso; }
    public int getCodigoPostal() { return codigoPostal; }
}
