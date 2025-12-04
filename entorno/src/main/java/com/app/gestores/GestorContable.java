package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.responsablePago.*;
import com.app.repository.PersonaFisicaRepository;
import com.app.repository.PersonaJuridicaRepository;

@Service
public class GestorContable{

    @Autowired
    private PersonaFisicaRepository personaFisicaRepository;
    @Autowired
    private PersonaJuridicaRepository personaJuridicaRepository;

    public ResponsablePago registrarResponsable(ResponsablePago responsable) {
        if (responsable instanceof PersonaFisica fisica) 
            return personaFisicaRepository.save(fisica);
        else if (responsable instanceof PersonaJuridica juridica) 
            return personaJuridicaRepository.save(juridica);
        else return null;
    }

}