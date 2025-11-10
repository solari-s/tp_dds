package com.app.gestores;

import com.app.responsablePago.PersonaFisicaDAO;
import com.app.responsablePago.PersonaFisicaDTO;

public class GestorResponsablePago {
 
    private static PersonaFisicaDAO datosTXT;
    //private static PersonaFisicaDAO datosMySQL; si hubiera otra

    public GestorResponsablePago(PersonaFisicaDAO fuente){
        datosTXT = fuente;
    }

    public void modificarPersona(PersonaFisicaDTO p){
        datosTXT.modificarPersona(p);
    }

    public void modificarPersona(PersonaFisicaDTO p, String CUITAnterior){
        datosTXT.modificarPersona(p, CUITAnterior);
    }
}
