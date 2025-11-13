package com.app.main.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHuesped {

    @GetMapping("/")
    public String mostrarPaginaBusqueda() {
        // Busca un archivo en src/main/resources/templates/
        return "altaHuesped";
    }

}
