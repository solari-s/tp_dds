package com.app.main.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.app.gestores.GestorHabitaciones;
import com.app.habitacion.HabitacionDTO;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class ControladorHabitacion {

    @Autowired
    private GestorHabitaciones gestorHabitaciones;

    @GetMapping("/estado")
    public List<HabitacionDTO> getEstadoHabitaciones(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date hasta) {
        return gestorHabitaciones.mostrarEstadoHabitaciones(desde, hasta);
    }
}