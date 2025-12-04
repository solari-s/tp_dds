package com.app.gestores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.huesped.Huesped;
import com.app.huesped.HuespedDTO;
import com.app.huesped.HuespedPK;
import com.app.repository.HuespedRepository;


@Service
public class GestorHuesped{
    
    @Autowired
    private HuespedRepository huespedRepository;

    public HuespedDTO darDeAltaHuesped(HuespedDTO huesped) {
        if(huesped!=null){
            Huesped huespedGuardar = new Huesped(huesped);
            huespedRepository.save(huespedGuardar);
            return huesped;
        }
        else return null;
    }

    public List<HuespedDTO> buscarHuespedes(HuespedDTO filtro) {
        Specification<Huesped> spec = Specification.unrestricted(); // base vacía
        String apellido = filtro.getApellido();
        String nombre = filtro.getNombre();
        String dni = filtro.getNroDocumento();
        String tipoDocumento = filtro.getTipo_documento().toString();

        if (apellido != null && !apellido.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(cb.lower(root.get("apellido")), apellido.toLowerCase())
            );
        }

        if (nombre != null && !nombre.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(cb.lower(root.get("nombre")), nombre.toLowerCase())
            );
        }

        if (dni != null && !dni.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("dni"), dni)
            );
        }

        if (tipoDocumento != null && !tipoDocumento.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(cb.lower(root.get("tipoDocumento")), tipoDocumento.toLowerCase())
            );
        }

        List<Huesped> entidades = huespedRepository.findAll(spec);

        
        return entidades.stream()
            .map(this::convertirADTO) // Llamamos a un método auxiliar por cada huesped
            .collect(Collectors.toList());
    }

    private HuespedDTO convertirADTO(Huesped entidad) {
    HuespedDTO dto = new HuespedDTO(entidad);
    return dto;
}
}

