package conserje;

public interface ConserjeDAO {

    // el optional permite que si no hay archivo lo tome como valido y no tire
    // excepcion, ona como un puede pasar que no este.
    java.util.Optional<Conserje> getConserje();

    void updateConserje(Conserje conserje);

}
