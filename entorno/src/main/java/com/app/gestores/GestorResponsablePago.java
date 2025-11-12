package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.responsablePago.ResponsablePago;
import com.app.responsablePago.ResponsablePagoRepository;


@Service
public class GestorResponsablePago{

    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    public ResponsablePago registrarResponsable(ResponsablePago responsable) {
        return responsablePagoRepository.save(responsable);
    }

    // ResponsablePago solamente aparece para q se le cargan los datos en esta entrega?

}

