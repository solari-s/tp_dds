package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.responsablePago.*;
import com.app.repository.PersonaFisicaRepository;
import com.app.repository.PersonaJuridicaRepository;
import com.app.repository.ResponsablePagoRepository;

@Service
public class GestorContable{

    @Autowired
    private PersonaFisicaRepository personaFisicaRepository;
    @Autowired
    private PersonaJuridicaRepository personaJuridicaRepository;
    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    public ResponsablePago registrarResponsable(ResponsablePago responsable) {
        
        ResponsablePago respp;
        if (responsable instanceof PersonaFisica fisica) 
            respp = personaFisicaRepository.save(fisica);
        else if (responsable instanceof PersonaJuridica juridica) 
            respp = personaJuridicaRepository.save(juridica);
        else return null;

        responsablePagoRepository.save(new ResponsablePago(respp.getCUIT()));

        return respp;
    }

}