package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.responsablePago.ResponsablePago;
import com.app.repository.ResponsablePagoRepository;


@Service
public class GestorResponsablePago{

    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    public ResponsablePago registrarResponsable(ResponsablePago responsable) {
        if(responsable!=null)
        return responsablePagoRepository.save(responsable);
        else return null;
    }

    // ResponsablePago solamente aparece para q se le cargan los datos en esta entrega?

}

