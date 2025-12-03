package com.app.gestores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.habitacion.Reserva;
import com.app.repository.ReservaRepository;

@Service
public class GestorReservas {
    
    @Autowired
    private ReservaRepository ReservaRepository;

    public Reserva HacerReserva(Reserva reserva) {
        return ReservaRepository.save(reserva);
    }
    
}
