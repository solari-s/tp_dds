package gestores;

import responsablePago.PersonaFisicaDAO;
import responsablePago.PersonaFisicaDAOtxt;
import responsablePago.PersonaFisicaDTO;

public class GestorResponsablePago {
 
    private static PersonaFisicaDAO datosTXT;
    //private static PersonaFisicaDAO datosMySQL; si hubiera otra

    public GestorResponsablePago(PersonaFisicaDAOtxt fuente){
        datosTXT = fuente;
    }

    public void modificarPersona(PersonaFisicaDTO p){
        datosTXT.modificarPersona(p);
    }

    public void modificarPersona(PersonaFisicaDTO p, String CUITAnterior){
        datosTXT.modificarPersona(p, CUITAnterior);
    }
}
