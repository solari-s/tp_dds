package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.huesped.Huesped;
import com.app.huesped.HuespedPK;
import com.app.huesped.HuespedRepository;


@Service
public class GestorHuesped{
    
    @Autowired
    private HuespedRepository huespedRepository;

    public Huesped darDeAltaHuesped(Huesped huesped) {
        return huespedRepository.save(huesped);
    }

    public Huesped buscarHuespedPorDoc(HuespedPK id) {
        return huespedRepository.findById(id).orElse(null);
    }

}

