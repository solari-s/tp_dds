package com.app.main.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHuesped {

    @GetMapping("/")
    public String mostrarPaginaBusqueda() {
        // Busca altaHuesped.html
        return "altaHuesped";
    }
    @GetMapping("/estado") 
    public String mostrarPaginaEstado() {
        // Busca "estadoHabitacion.html" 
        return "estadoHabitacion"; 
    }
}
