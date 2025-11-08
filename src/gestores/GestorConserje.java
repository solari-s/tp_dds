package gestores;

import java.util.Optional;

import conserje.Conserje;
import conserje.ConserjeDAO;

public class GestorConserje {

    private final ConserjeDAO conserjeDAO;

    public GestorConserje(ConserjeDAO conserjeDAO) {
        this.conserjeDAO = conserjeDAO;
    }

    public boolean autentificarConserje(String usuario, String contrasenia) {

        //llave booleana
        boolean autenticado = false;

        //guarda el conserje de la base de datos en el conserje del gestor
        Optional<Conserje> conserjeValido;
        conserjeValido = conserjeDAO.getConserje();
        
        //hacer validacion de conserje empty ? error : continuar
        if (conserjeValido.isEmpty()) {
            System.out.println("Error: No se pudo obtener el conserje de la base de datos.");
            return false;
        }

        // genera la instancia en la que guardamos los datos a validar
        Conserje conserje = new Conserje(usuario, contrasenia);

        // variables para validar los campos
        boolean nombreValido = conserje.getNombre().equals(conserjeValido.get().getNombre());
        boolean contraseniaValida = conserje.getContrasenia().equals(conserjeValido.get().getContrasenia());

        // verifiacion
        autenticado = (nombreValido && contraseniaValida) ? true : false;
        return autenticado;
    }

    // actualizar datos (ponerlo?)
    public void actualizarConserje(String usuario, String contrasenia) {
        Conserje conserje = new Conserje(usuario, contrasenia);
        conserjeDAO.updateConserje(conserje);
    }

}
