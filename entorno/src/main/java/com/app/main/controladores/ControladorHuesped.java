package com.app.main.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.gestores.GestorHuesped;
import com.app.huesped.Huesped;



@Controller
public class ControladorHuesped {

    @GetMapping("/")
    public String mostrarPaginaBusqueda() {
        // Busca altaHuesped.html
        return "buscarHuesped";
    }

    @Autowired
    private GestorHuesped GestorHuesped;

    @GetMapping("/buscarHuesped")
    public String buscarHuesped(
        @RequestParam(required = false) String apellido,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String dni,
        @RequestParam(required = false) String tipoDocumento,
        Model model) {

        List<Huesped> resultados = GestorHuesped.buscarHuespedes(apellido, nombre, dni, tipoDocumento);
        model.addAttribute("resultados", resultados);   

        return "resultadoBusquedaHuesped"; // Thymeleaf HTML
    }


    @GetMapping("/estado") 
    public String mostrarPaginaEstado() {
        // Busca "estadoHabitacion.html" 
        return "estadoHabitacion"; 
    }
}
