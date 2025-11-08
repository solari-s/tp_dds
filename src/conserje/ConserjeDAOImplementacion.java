package conserje;

//Librerias nio para usar el txt
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import java.util.Optional;

public class ConserjeDAOImplementacion implements ConserjeDAO {

    // tomi peiretti dijo que el acceso directo al archivo y su modifciacion es lo
    // mas sencillo pero que hagamsocomo queramos

    // Ruta del archivo de texto
    private final Path rutaArchivo;

    // Constructor que inicializa la ruta del archivo, poner el archivo ahi en
    // conserje
    public ConserjeDAOImplementacion() {
        this.rutaArchivo = Paths.get("conserje.txt");
    }

    @Override
    public Optional<Conserje> getConserje() {
        try {
            // si no existe
            if (!Files.exists(rutaArchivo)) {
                return Optional.empty();
            }
            // lee el archivo en linea
            String linea = Files.readString(rutaArchivo, StandardCharsets.UTF_8).trim();

            // si no lee nada
            if (linea.isEmpty()) {
                return Optional.empty();
            }

            // divide la linea en nombre y contraseña
            String[] partes = linea.split(";", 2);

            // chequeo de que esten dos partes y si estan manda, sino retorna vacio
            return (partes.length == 2) ? Optional.of(new Conserje(partes[0], partes[1])) : Optional.empty();

        } catch (java.io.IOException e) {

            System.err.println("Error al leer el archivo de conserje: " + e.getMessage());

            return Optional.empty();
        }
    }

    @Override
    public void updateConserje(Conserje conserje) {
        try {
            // escribe el nombre y la contraseña en el archivo, separados por ; en linea
            String linea = conserje.getNombre() + ";" + conserje.getContrasenia();

            // escribe en el archivo, el truncate lo va a sobreescribir
            Files.writeString(
                    rutaArchivo, linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            // no se bien como funciona el try y catch tdv, pero estoe s necesario(?
            throw new RuntimeException(e);
        }
    }

}