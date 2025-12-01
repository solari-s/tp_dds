package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repository.ReservaRepository;

@Service
public class GestorReservas {
    
    @Autowired
    private ReservaRepository ReservaRepository;

    
}
