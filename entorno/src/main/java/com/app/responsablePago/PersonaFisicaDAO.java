package com.app.responsablePago;



import com.app.huesped.TipoDoc;

public interface PersonaFisicaDAO{

    //busqueda 
    public PersonaFisicaDTO buscarPorDocumento(TipoDoc doc,String DNI);

    //insercion
    public void insertarPersona(PersonaFisicaDTO p);

    //eliminacion
    public void eliminarPersona(PersonaFisicaDTO p);

    //modificacion
    public void modificarPersona(PersonaFisicaDTO p);
    public void modificarPersona(PersonaFisicaDTO p,String CUITAnterior);
}
