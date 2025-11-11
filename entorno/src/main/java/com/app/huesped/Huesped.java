package com.app.huesped;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@IdClass(HuespedPK.class)
public class Huesped {

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Id
    @Enumerated(EnumType.STRING)
    private TipoDoc tipo_documento;

    @Id
    private String nroDocumento;

    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;

    @Column(nullable = false)
    private String nacionalidad;
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String ocupacion;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN CHECK (alojado IN (0,1))")
    private boolean alojado;


    protected Huesped() {} //JPA requiere un constructor público o protegido sin argumentos para poder instanciar el objeto mediante reflexión.

    public Huesped(String nombre, String apellido, TipoDoc tipo_documento, String nroDocumento,
            Date fechaDeNacimiento, String nacionalidad, String email,
            String telefono, String ocupacion, boolean alojado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo_documento = tipo_documento;
        this.nroDocumento = nroDocumento;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.telefono = telefono;
        this.ocupacion = ocupacion;
        this.alojado = alojado;
    }

    public Huesped(HuespedDTO huespedDto) {
        this.nombre = huespedDto.getNombre();
        this.apellido = huespedDto.getApellido();
        this.tipo_documento = huespedDto.getTipo_documento();
        this.nroDocumento = huespedDto.getNroDocumento();
        this.fechaDeNacimiento = huespedDto.getFechaDeNacimiento();
        this.nacionalidad = huespedDto.getNacionalidad();
        this.email = huespedDto.getEmail();
        this.telefono = huespedDto.getTelefono();
        this.ocupacion = huespedDto.getOcupacion();
        this.alojado = huespedDto.isAlojado();
    }


    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoDoc getTipo_documento() {
        return tipo_documento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public boolean isAlojado() {
        return alojado;
    }

}