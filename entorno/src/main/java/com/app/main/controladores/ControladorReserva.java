package com.app.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.gestores.GestorReservas;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaControlador {

    @Autowired
    private GestorReservas gestorReservas;

    @GetMapping("/buscar")
    public List<Map<String, Object>> buscar(@RequestParam String tipo) {
        return gestorReservas.buscarDisponibilidad(tipo);
    }

    @PostMapping("/crear")
    public String crearReserva(@RequestParam String tipo, @RequestParam String fecha, @RequestParam int numero) {
        return gestorReservas.crearReserva(tipo, fecha, numero);
    }

}