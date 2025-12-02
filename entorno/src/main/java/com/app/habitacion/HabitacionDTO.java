package com.app.habitacion;

public class HabitacionDTO {

    private TipoHabitacion tipo;
    private int numero;
    private float costoNoche;
    private HistorialEstadoHabitacionDTO historialEstados;
    private Reserva reservas;

    // Getter y Setter para tipo
    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    // Getter y Setter para numero
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Getter y Setter para costoNoche
    public float getCostoNoche() {
        return costoNoche;
    }

    public void setCostoNoche(float costoNoche) {
        this.costoNoche = costoNoche;
    }

    // Getter y Setter para historialEstados
    public HistorialEstadoHabitacionDTO getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(HistorialEstadoHabitacionDTO historialEstados) {
        this.historialEstados = historialEstados;
    }

    // Getter y Setter para reservas
    public Reserva getReservas() {
        return reservas;
    }

    public void setReservas(Reserva reservas) {
        this.reservas = reservas;
    }
}
