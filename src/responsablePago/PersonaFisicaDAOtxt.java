package responsablePago;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import huesped.HuespedDTO;
import huesped.TipoDoc;

public class PersonaFisicaDAOtxt implements PersonaFisicaDAO {

    private static final String ARCHIVO_PERSONAS = "bddPersonaFisica.txt";
    private static final String ARCHIVO_TEMPORAL = "bdd_temp.txt";
    
    public PersonaFisicaDTO buscarPorDocumento(TipoDoc doc, String DNI) {

        try {
            BufferedReader bdd = new BufferedReader(new FileReader(ARCHIVO_PERSONAS));
            String registro = null;

            while (bdd.ready()) {

                registro = bdd.readLine();
                String result[] = dividirRegistro(registro);
                if (result[2].trim().equals(DNI.trim()) && result[3].trim().equals(doc.toString())) {
                    HuespedDTO h = new HuespedDTO();
                    h.setNroDocumento(DNI);
                    h.setTipo_documento(doc);
                    // [CUIT PosIVA Doc TipoDoc] 
                    PersonaFisicaDTO coincidente = new PersonaFisicaDTO(result[0], result[1], h);
                    
                    bdd.close();
                    return coincidente;
                }
            }
            bdd.close();
        } catch (Exception e) {
            System.out.println("no hay conexion con la bdd");
        }
        
        return null;
    }

    // INSERCION asumiendo que no existe un orden dentro del archivo
    public void insertarPersona(PersonaFisicaDTO p) {

        try {
            BufferedWriter bdd = new BufferedWriter(new FileWriter(ARCHIVO_PERSONAS, true));
            // [CUIT PosIVA Doc TipoDoc] necesito asociar al huesped con su doc y tipodoc
            String registro = p.getCUIT() + "|" + p.getPosicionIVA() + "|" + p.getHuesped().getNroDocumento() + "|"
                    + p.getHuesped().getTipo_documento();

            bdd.write(registro);
            bdd.newLine();

            System.out.println("Persona fisica insertada correctamente");
            bdd.close();
        } catch (Exception e) {
            System.out.println("no hay conexion con la bdd");
        }

    }

    // ELIMINACION
    public void eliminarPersona(PersonaFisicaDTO p) {

        boolean modificado = false;

        File archivo = new File(ARCHIVO_PERSONAS);
        File archivoTemporal = new File(ARCHIVO_TEMPORAL);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;
            while ((linea = reader.readLine()) != null) {

                if (linea.contains(p.getCUIT())) {
                    // es el que voy a eliminar, lo descarto
                    modificado = true;
                } else {
                    // no es, va la linea original
                    writer.write(linea);
                    writer.newLine();
                }

            }
            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al eliminar la persona: " + e.getMessage());
            return; // Salimos si hay un error
        }

        archivo.delete();
        archivoTemporal.renameTo(archivo);

        if (modificado) {
            System.out.println("Persona eliminada correctamente.");
        } else {
            System.out.println("No se encontró la persona a eliminar.");
        }
    }

    // MODIFICACION
    public void modificarPersona(PersonaFisicaDTO p) {

        boolean modificado = false;

        File archivo = new File(ARCHIVO_PERSONAS);
        File archivoTemporal = new File(ARCHIVO_TEMPORAL);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;
            while ((linea = reader.readLine()) != null) {

                if (linea.contains(p.getCUIT())) {
                    // agrego el nuevo registro
                    String registroNuevo = toStringTXT(p);
                    modificado = true;
                    writer.write(registroNuevo);
                    writer.newLine();
                } else {
                    // no es, va la linea original
                    writer.write(linea);
                    writer.newLine();
                }

            }
            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al modificar la persona: " + e.getMessage());
            return; // Salimos si hay un error
        }

        archivo.delete();
        archivoTemporal.renameTo(archivo);

        if (modificado) {
            System.out.println("Persona modificada correctamente.");
        } else {
            System.out.println("No se encontró la persona a modificar.");
        }

    }

    /*
     * si lo que se modifica es el CUIT no tengo forma de encontrarlo en la bdd
     * ya que es el criterio de búsqueda asique paso el anterior como parámetro
     * p ya tiene el CUIT nuevo
     */
    public void modificarPersona(PersonaFisicaDTO p, String CUITAnterior) {
        boolean modificado = false;

        File archivo = new File(ARCHIVO_PERSONAS);
        File archivoTemporal = new File(ARCHIVO_TEMPORAL);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;
            while ((linea = reader.readLine()) != null) {

                if (linea.contains(CUITAnterior)) {
                    // agrego el nuevo registro
                    String registroNuevo = toStringTXT(p);
                    modificado = true;
                    writer.write(registroNuevo);
                    writer.newLine();
                } else {
                    // no es, va la linea original
                    writer.write(linea);
                    writer.newLine();
                }

            }
            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al modificar la persona: " + e.getMessage());
            return; // Salimos si hay un error
        }

        archivo.delete();
        archivoTemporal.renameTo(archivo);

        if (modificado) {
            System.out.println("Persona modificada correctamente.");
        } else {
            System.out.println("No se encontró la persona a modificar.");
        }
    }

    //AUXILIARES
    /*
     * NO TOCAR función aux para dividir el registro.
     * Retorna el registro con la estructura CUIT|PosIVA|Doc|TipoDoc en un arreglo
     */
    private String[] dividirRegistro(String registro) {

        String[] partes = new String[4];
        int i = 0;
        int puntInicio = 0;
        int puntFin;

        while (i < 3) { // Solo busca los primeros 3 separadores
            puntFin = registro.indexOf("|", puntInicio);
            if (puntFin == -1)
                break; // Si no hay más separadores
            partes[i] = registro.substring(puntInicio, puntFin).trim();
            puntInicio = puntFin + 1;
            i++;
        }

        // ultimo campo sin separador
        if (i < 4 && puntInicio < registro.length()) {
            partes[i] = registro.substring(puntInicio).trim();
        }

        return partes;

    }
    
    //Retorna el registro con la estructura CUIT|PosIVA|Doc|TipoDoc
    private String toStringTXT(PersonaFisicaDTO p) {
        return p.getCUIT() + "|" + p.getPosicionIVA() + "|" + p.getHuesped().getNroDocumento() + "|"
                + p.getHuesped().getTipo_documento().toString();
    }
}
