package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
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

}

