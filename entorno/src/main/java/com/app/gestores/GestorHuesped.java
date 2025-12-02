package com.app.gestores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.huesped.Huesped;
import com.app.huesped.HuespedPK;
import com.app.repository.HuespedRepository;


@Service
public class GestorHuesped{
    
    @Autowired
    private HuespedRepository huespedRepository;

    public Huesped darDeAltaHuesped(Huesped huesped) {
        if(huesped!=null)
        return huespedRepository.save(huesped);
        else return null;
    }

    public Huesped buscarHuespedPorDoc(HuespedPK id) {
        if(id!=null)
        return huespedRepository.findById(id).orElse(null);
        else return null;
    }

    public List<Huesped> buscarHuespedes(String apellido, String nombre, String dni, String tipoDocumento) {
        Specification<Huesped> spec = Specification.unrestricted(); // base vacía

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

        return huespedRepository.findAll(spec);
    }

}

