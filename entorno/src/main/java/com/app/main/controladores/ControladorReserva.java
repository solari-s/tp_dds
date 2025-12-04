package com.app.main.controladores;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.gestores.GestorReservas;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ControladorReserva {

    @Autowired
    private GestorReservas gestorReservas;

    @GetMapping("/buscar")
    public List<Map<String, Object>> buscar(
            @RequestParam String tipo,
            @RequestParam String desde,
            @RequestParam String hasta) {
        return gestorReservas.buscarDisponibilidad(tipo, desde, hasta);
    }

    @PostMapping("/crear")
    public String crearReserva(@RequestParam String tipo, @RequestParam String fecha, @RequestParam int numero) {
        return gestorReservas.crearReserva(tipo, fecha, numero);
    }

}