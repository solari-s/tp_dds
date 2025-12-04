package com.app.direccion;

import java.util.ArrayList;
import java.util.List;

public class ProvinciaDTO {

    private String nombre;
    private Pais pais;
    private List<Localidad> localidades = new ArrayList<>();

    public ProvinciaDTO() {
    }

    public ProvinciaDTO(String nombre, Pais pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }
}
