package huesped;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HuespedDAOtxt implements HuespedDAO {
    private static final String ARCHIVO_HUESPEDES = "huespedes.txt";
    private static final String ARCHIVO_TEMPORAL = "huespedes_temp.txt";

    // Guarda un nuevo huésped a partir de su DTO.
    public boolean darDeAltaHuesped(HuespedDTO huespedDto) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_HUESPEDES, true));
            String linea = convertirDtoALinea(huespedDto);
            writer.write(linea);
            writer.newLine(); // Añade el salto de línea
            writer.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error al dar de alta al huésped: " + e.getMessage());
            return false;
        }
    }

    // Modifica los datos de un huésped existente a partir de su DTO.
    public void modificarHuesped(HuespedDTO huespedDto) {
        boolean modificado = false;

        File archivo = new File(ARCHIVO_HUESPEDES);
        File archivoTemporal = new File(ARCHIVO_TEMPORAL);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                String nroDoc = partes[3];

                if (nroDoc.equals(huespedDto.getNroDocumento())) {
                    // Encontramos al huésped, escribimos la nueva información
                    String nuevaLinea = convertirDtoALinea(huespedDto);
                    writer.write(nuevaLinea);
                    modificado = true;
                } else {
                    // No es el huésped, escribimos la línea original
                    writer.write(linea);
                }
                writer.newLine();
            }
            reader.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al modificar el huésped: " + e.getMessage());
            return; // Salimos si hay un error
        }

        archivo.delete();
        archivoTemporal.renameTo(archivo);

        if (modificado) {
            System.out.println("Huésped modificado correctamente.");
        } else {
            System.out.println("No se encontró el huésped para modificar.");
        }
    }

    // Elimina un huésped de la fuente de datos.
    public void darDeBajaHuesped(HuespedDTO huespedDto) {

    // Usamos la API 'nio' (New I/O), que es más moderna y
    // se integra mejor con Streams.
    Path archivoPath = Paths.get(ARCHIVO_HUESPEDES);
    String nroDocBaja = huespedDto.getNroDocumento();
    boolean eliminado = false;

    try {
        
        List<String> lineasOriginales = Files.readAllLines(archivoPath);

        // Usamos STREAMS y LAMBDA para filtrar
        List<String> lineasFiltradas = lineasOriginales.stream()
            
            // filter() MANTIENE los elementos que devuelven 'true'
            .filter(linea -> {
                String[] partes = linea.split(";");
                String nroDoc = partes[3]; // Asumimos índice 3
                
                // Mantenemos la línea SI el DNI NO es igual
                return !nroDoc.equals(nroDocBaja);
            })
            
            
            .collect(Collectors.toList());
        
        //Comparamos tamaños para saber si algo se eliminó
        if (lineasOriginales.size() > lineasFiltradas.size()) {
            eliminado = true;
        }

        
        Files.write(archivoPath, lineasFiltradas);

        
        if (eliminado) {
            System.out.println("Huésped dado de baja correctamente.");
        } else {
            System.out.println("No se encontró el huésped.");
        }

    } catch (IOException e) {
        System.out.println("Error al acceder al archivo: " + e.getMessage());
    }
}

    // Busca un huésped por documento y devuelve el objeto de dominio completo.
    public HuespedDTO buscarHuesped(TipoDoc tipoDoc, String nroDoc) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HUESPEDES));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes[3].equals(nroDoc) && partes[2].equals(tipoDoc.toString())) {
                    reader.close();
                    return convertirLineaADto(partes);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al buscar el huésped: " + e.getMessage());
        }

        // Si el bucle termina, no se encontró
        return null;
    }

    // NUEVO Busca un huésped por nombre o apellido y devuelve el objeto de dominio
    // completo.
    public List<HuespedDTO> buscarHuesped(String criterio) {
        List<HuespedDTO> encontrados = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HUESPEDES));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes[0].equalsIgnoreCase(criterio) || partes[1].equalsIgnoreCase(criterio)) {
                    encontrados.add(convertirLineaADto(partes));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al buscar el huésped: " + e.getMessage());
        }

        return encontrados;
    }


    /**
     * Convierte un objeto HuespedDTO a un String separado por ";".
     * Formato: Nombre;Apellido;TipoDoc;NroDocumento;fechaDeNacimiento;...;alojado
     */
    private String convertirDtoALinea(HuespedDTO dto) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Concatenamos todos los campos usando el operador +
        String linea = dto.getNombre() + ";"
                + dto.getApellido() + ";"
                + dto.getTipo_documento().toString() + ";" // Convertir Enum a String
                + dto.getNroDocumento() + ";"
                + formatter.format(dto.getFechaDeNacimiento()) + ";" 
                + dto.getNacionalidad() + ";"
                + dto.getEmail() + ";"
                + dto.getTelefono() + ";"
                + dto.getOcupacion() + ";"
                + dto.isAlojado(); // Convierte boolean a "true" o "false"

        return linea;
    }

    /**
     * Convierte un array de Strings (partes de una línea) en un HuespedDTO.
     */
    private HuespedDTO convertirLineaADto(String[] partes) {

        HuespedDTO dto = new HuespedDTO();

        dto.setNombre(partes[0]);
        dto.setApellido(partes[1]);
        dto.setTipo_documento(TipoDoc.valueOf(partes[2])); // Convierte String a Enum
        dto.setNroDocumento(partes[3]);
        dto.setFechaDeNacimiento(partes[4]); // Asumimos que se guarda como String
        dto.setNacionalidad(partes[5]);
        dto.setEmail(partes[6]);
        dto.setTelefono(partes[7]);
        dto.setOcupacion(partes[8]);
        dto.setAlojado(Boolean.parseBoolean(partes[9])); // Convierte "true" a boolean

        return dto;
    }
}
