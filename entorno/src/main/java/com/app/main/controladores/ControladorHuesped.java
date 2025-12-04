package com.app.main.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.gestores.GestorHuesped;
import com.app.huesped.Huesped;
import com.app.huesped.HuespedDTO;
import com.app.huesped.TipoDoc;



@Controller
public class ControladorHuesped {

    @Autowired
    private GestorHuesped gestorHuesped;

    @GetMapping("/altaHuesped")
    public String altaHuesped(Model model) {
        model.addAttribute("huesped", new HuespedDTO());
        model.addAttribute("tiposDocumento", TipoDoc.values());
        return "altaHuesped";
    }

    @PostMapping("/altaHuesped")
    public String crearHuesped(@ModelAttribute HuespedDTO huespedDTO) {
        gestorHuesped.darDeAltaHuesped(huespedDTO);
        return "redirect:/";
    }

    // Método para cargar la vista inicial
    @GetMapping("/buscarHuesped")
    public String buscarHuesped(Model model) {
        model.addAttribute("tiposDocumento", TipoDoc.values());
        return "buscarHuesped";
    }

    // NUEVO: Endpoint API para realizar la búsqueda dinámica
    // Retorna JSON para ser consumido por el JavaScript de la vista
    @GetMapping("/api/huespedes/buscar")
    @ResponseBody
    public ResponseEntity<List<HuespedDTO>> buscarHuespedesAPI(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String documento) {

        HuespedDTO filtro = new HuespedDTO();
        
        // Asignamos solo si no están vacíos para que el Gestor filtre correctamente
        if (nombre != null && !nombre.isEmpty()) filtro.setNombre(nombre);
        if (apellido != null && !apellido.isEmpty()) filtro.setApellido(apellido);
        if (tipoDocumento != null && !tipoDocumento.trim().isEmpty()) {
            try {
                filtro.setTipo_documento(TipoDoc.valueOf(tipoDocumento));
            } catch (IllegalArgumentException e) {
                // Si el tipo de documento no es válido, lo ignoramos o manejamos el error
                System.out.println("Tipo de documento inválido: " + tipoDocumento);
            }
        }
        if (documento != null && !documento.isEmpty()) filtro.setNroDocumento(documento);

        List<HuespedDTO> resultados = gestorHuesped.buscarHuespedes(filtro);
        return ResponseEntity.ok(resultados);
   }


    @GetMapping("/estado") 
    public String mostrarPaginaEstado() {
        // Busca "estadoHabitacion.html" 
        return "estadoHabitacion"; 
    }

    
}
