package com.app.main.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.gestores.GestorContable;
import com.app.gestores.GestorHuesped;
import com.app.huesped.Huesped;
import com.app.huesped.HuespedDTO;
import com.app.huesped.TipoDoc;

@Controller
public class ControladorHuesped {

    @Autowired
    private GestorHuesped gestorHuesped;

    @Autowired
    private GestorContable gestorContable;

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
        if (nombre != null && !nombre.isEmpty())
            filtro.setNombre(nombre);
        if (apellido != null && !apellido.isEmpty())
            filtro.setApellido(apellido);
        if (tipoDocumento != null && !tipoDocumento.trim().isEmpty()) {
            try {
                filtro.setTipo_documento(TipoDoc.valueOf(tipoDocumento));
            } catch (IllegalArgumentException e) {
                // Si el tipo de documento no es válido, lo ignoramos o manejamos el error
                System.out.println("Tipo de documento inválido: " + tipoDocumento);
            }
        }
        if (documento != null && !documento.isEmpty())
            filtro.setNroDocumento(documento);

        List<HuespedDTO> resultados = gestorHuesped.buscarHuespedes(filtro);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/estado")
    public String mostrarPaginaEstado() {
        // Busca "estadoHabitacion.html"
        return "estadoHabitacion";
    }

    @GetMapping("/reservar")
    public String mostrarPaginaReserva() {
        return "reservarHabitacion"; // Busca reservarHabitacion.html
    }

    // Endpoint para recibir el JSON desde el fetch de JavaScript
    @PostMapping("/api/huespedes/crear")
    @ResponseBody
    public ResponseEntity<?> crearHuespedAPI(@RequestBody ContenedorDeAltaHuesped request) { // <-- CAMBIO AQUÍ
        try {
            // PASO 1: Registrar Huésped (GestorHuesped)
            Huesped huespedGuardado = gestorHuesped.darDeAltaHuesped(request.getHuesped());

            // PASO 2: Registrar Responsable Fiscal (GestorContable)
            // Solo si mandaron datos de persona física
            if (request.getPersonaFisica() != null &&
                    request.getPersonaFisica().getCUIT() != null &&
                    !request.getPersonaFisica().getCUIT().isEmpty()) {

                gestorContable.registrarPersonaFisica(request.getPersonaFisica(), huespedGuardado);
            }

            return ResponseEntity.ok().body("{\"message\": \"Huesped y Responsable creados exitosamente\"}");

        } catch (Exception e) {
            e.printStackTrace(); // Útil para ver errores en consola del servidor
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
