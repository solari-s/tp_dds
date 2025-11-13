package com.app.main.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorHuesped {

    @GetMapping("/")
    public String mostrarPaginaBusqueda() {
        // Busca un archivo en src/main/resources/templates/
        return "altaHuesped";
    }

}