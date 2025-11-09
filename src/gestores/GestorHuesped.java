package gestores;

import responsablePago.PersonaFisicaDTO;
import responsablePago.PersonaFisicaDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import huesped.HuespedDAO;
import huesped.HuespedDTO;
import huesped.TipoDoc;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class GestorHuesped {
    private HuespedDAO huespedDAO;

    public GestorHuesped(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }

    // Guarda un nuevo huésped
    public void darDeAltaHuesped(HuespedDTO huespedDto, PersonaFisicaDTO persona) {
    Scanner scanner = new Scanner(System.in);
    String repetirProceso = "NO";
    do{
        int opcion = 0;

        do {
            if (huespedDAO.buscarHuesped(huespedDto.getTipo_documento(), huespedDto.getNroDocumento()) != null) {
                System.out.println("¡CUIDADO! El tipo y número de documento ya existen en el sistema");
                System.out.println("Inserte 1 si desea ACEPTAR IGUALMENTE o 2 para corregir.");

                opcion = scanner.nextInt();
                if (opcion == 2) {
                    System.out.print("Ingrese tipo de documento: ");
                    String tipoDocStr = scanner.nextLine();
                    TipoDoc tipoDoc = TipoDoc.valueOf(tipoDocStr.toUpperCase());
                    huespedDto.setTipo_documento(tipoDoc);

                    System.out.print("Número de documento: ");
                    String nroDoc = scanner.nextLine();
                    huespedDto.setNroDocumento(nroDoc);
                }
            }
        } while (opcion == 2);
        PersonaFisicaDAOtxt personaDAO = new PersonaFisicaDAOtxt();
        personaDAO.insertarPersona(persona);
        if (huespedDAO.darDeAltaHuesped(huespedDto)) {
            System.out.println("El huésped " + huespedDto.getNombre() + " y " +
                    huespedDto.getApellido() + " ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro?");
            System.out.println("INSERTE SI O NO"); 
            repetirProceso = scanner.nextLine();
        }else{
            System.out.println("No se ha podido cargar el huesped, ¿deseas intentarlo nuevamente?");
            System.out.println("INSERTE SI O NO");
            repetirProceso = scanner.nextLine();
        }
    }while(repetirProceso.equalsIgnoreCase("SI"));
    }

    // Modifica los datos de un huésped existente a partir de su DTO.
    public static void modificarHuesped(HuespedDTO hdto, PersonaFisicaDTO p) {

        boolean huboError = true;
        boolean modificado = false;
        boolean cancelar = false;
        HuespedDTO huespedModificado = hdto;
        PersonaFisicaDTO personaModificado = p;
        BufferedReader entrada;
        boolean ingresaSiguiente, ingresaBorrar = false;
        boolean modificaDNI=false;
        //huboError => SE QUIERE BORRAR EL HUESPED
        while (huboError && !modificado && !cancelar) {
            ingresaSiguiente = false;
            boolean cancelarModificar = false;

            mostrarDatos(hdto, p);
            System.out.println("SIGUIENTE = S | CANCELAR = C | BORRAR = B");

            do {
                try {
                    System.out.print("Seleccione el indice a modificar (S para SIGUIENTE): ");
                    entrada = new BufferedReader(new InputStreamReader(System.in));
                    String indice = entrada.readLine();// indice a modificar

                    if (indice.toLowerCase().equals("s")) {
                        ingresaSiguiente = true;

                    } else if (indice.toLowerCase().equals("b")) {
                        ingresaBorrar = true;

                    } else if(indice.toLowerCase().equals("c")){
                        System.out.println("¿Desea cancelar la modificación del huésped?");
                        System.out.println("1) Si");
                        System.out.println("2) No");
                        System.out.print("Introduzca el numero correspondiente a su elección: ");
                        indice = entrada.readLine();
                        
                        if(Integer.parseInt(indice)==1){
                            ingresaSiguiente=true;
                            cancelar= true;
                        }
                        else if(Integer.parseInt(indice)==2){
                            cancelarModificar= true;
                        }
                        //Si ingresa otro numero?
                    } else if(Integer.parseInt(indice) >= 1 && Integer.parseInt(indice) <= 12){
                        System.out.print("Valor nuevo para el campo " + indice + ": ");
                        entrada = new BufferedReader(new InputStreamReader(System.in));
                        String elementoNuevo = entrada.readLine();
                        setElemento(personaModificado, huespedModificado, elementoNuevo, Integer.parseInt(indice));

                        if(Integer.parseInt(indice)==4)modificaDNI=true;
                        if(verificarCamposObligatoriosIncompletos(huespedModificado, modificaDNI)
                            && verificarCamposObligatoriosIncompletos(personaModificado)){
                            HuespedDAO hDAO = new HuespedDAOtxt();
                            hDAO.modificarHuesped(huespedModificado);
                            PersonaFisicaDAO perDAO = new PersonaFisicaDAOtxt();
                            perDAO.modificarPersona(personaModificado);
                            modificado=true;
                        }
                        
                    }
                } catch (IOException e) {
                    System.out.println("Error de lectura por consola");
                }
            } while (!ingresaSiguiente && !ingresaBorrar && !cancelarModificar);

            if (ingresaBorrar) {
                huboError = false;
            } else if (verificarCamposObligatoriosIncompletos(huespedModificado, modificaDNI)
                    && verificarCamposObligatoriosIncompletos(personaModificado)) {
                huboError = false;
            }

        }

        if (ingresaBorrar)
            eliminarHuesped(hdto);
        else {
            System.out.println("Cambios terminados correctamente");
            if(modificado){
                mostrarDatos(huespedModificado, personaModificado);
            }
        }

    }

    // Elimina un huésped de la fuente de datos.
    public static void eliminarHuesped(HuespedDTO dto) {
        Scanner sc = new Scanner(System.in);

        if (dto.isAlojado()) {

            // no se puede dar de baja
            System.out.println(
                    "El huésped no puede ser eliminado pues se ha alojado en el Hotel en alguna oportunidad.\n");
            System.out.println("PRESIONE CUALQUIER TECLA PARA CONTINUAR…");
            sc.nextLine(); // espera la acción del usuario
            // El CU termina

        } else { // se puede dar de baja

            System.out.println("Los datos del huésped " + dto.getNombre() + " " + dto.getApellido() + " ("
                    + dto.getTipo_documento() + " " + dto.getNroDocumento() + ") serán eliminados del sistema.");

            System.out.println("1. Eliminar");
            System.out.println("2. Cancelar");
            System.out.print("Introduzca el numero correspondiente a su elección: ");

            int select;

            do {
                // leer opción
                select = sc.nextInt();

                if (select == 1) { // se elimina al huesped
                    HuespedDAO h = new HuespedDAOtxt();
                    h.darDeBajaHuesped(dto);

                    System.out.println("Los datos del huésped " + dto.getNombre() + " " + dto.getApellido() + " ("
                            + dto.getTipo_documento() + " " + dto.getNroDocumento()
                            + ") han sido eliminados del sistema.");
                    System.out.println("PRESIONE CUALQUIER TECLA PARA CONTINUAR…");
                    sc.nextLine();
                } else if (select == 2) { // se cancela

                } else
                    System.out.println("Ingrese una de las dos opciones disponibles!");
            } while (select != 1 && select != 2);

        }

        
    }

    // Caso de uso 2 - Buscar Huésped
    public void ejecutarCU2() {
        Scanner sc = new Scanner(System.in); //se cierra en main
        List<HuespedDTO> encontrados = new ArrayList<>();

        System.out.println(
                "Ingrese los criterios de búsqueda (puede dejar campos vacíos):");

        System.out.print("Apellido: ");
        String apellido = sc.nextLine().trim().toUpperCase();

        System.out.print("Nombres: ");
        String nombre = sc.nextLine().trim().toUpperCase();

        System.out.print("Tipo de documento [DNI, LE, LC, PASAPORTE, OTRO]:");
        String tipoStr = sc.nextLine().trim().toUpperCase();

        TipoDoc tipoDoc = null;
        if (!tipoStr.isEmpty()) {
            try {
                tipoDoc = TipoDoc.valueOf(tipoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de documento inválido. Se ignorará este campo.");
            }
        }

        System.out.print("Número de documento: ");
        String nroDoc = sc.nextLine().trim();

        HuespedDTO encontradoPorDoc = null;

        // Si se completó documento, priorizamos la búsqueda exacta
        if (tipoDoc != null && !nroDoc.isEmpty()) {
            encontradoPorDoc = huespedDAO.buscarHuesped(tipoDoc, nroDoc);
            if (encontradoPorDoc != null)
            encontrados.add(encontradoPorDoc);
        }

        // Buscar también por nombre o apellido si se ingresaron
        if (!apellido.isEmpty()) {
        List<HuespedDTO> listaHuespedes = huespedDAO.buscarHuesped(apellido);

            if (listaHuespedes != null) {
                for (HuespedDTO h : listaHuespedes) {
                    if (!encontrados.contains(h)) {
                        encontrados.add(h);
                    }
                }
            }
        }
        
        if (!nombre.isEmpty()) {
            List<HuespedDTO> listaHuespedes = huespedDAO.buscarHuesped(nombre);
            if(listaHuespedes != null){
                for(HuespedDTO h : listaHuespedes){
                    if(!encontrados.contains(h)){
                        encontrados.add(h);
                    }
                }
            }
        }

        // Evaluar resultados
        
        if (encontrados.isEmpty()) {

            System.out.println("No se encontró ningún huésped con esos datos. Ejecutando CU9 (Dar de alta huésped)");

            System.out.println("Rellene todos los campos para generar un huesped, los campos EMAIL, CUIT y POSICIONIVA no son obligatorios");
            HuespedDTO nuevoH = new HuespedDTO();
            PersonaFisicaDTO nuevoP = new PersonaFisicaDTO(nuevoH);
            while(!verificarCamposObligatoriosIncompletos(nuevoH, false) || !verificarCamposObligatoriosIncompletos(nuevoP) ){
                mostrarDatos(nuevoH, nuevoP);
                String indice;
                String seleccionar;
                System.out.print("INDICE: ");
                indice = sc.nextLine();
                seleccionar = sc.nextLine();
                setElemento(nuevoP, nuevoH, seleccionar, Integer.parseInt(indice));
            }

            darDeAltaHuesped(nuevoH, nuevoP);
        }

        if (encontrados.size() == 1) {
            HuespedDTO unico = encontrados.get(0);
            System.out.println("Huésped encontrado:");
                    System.out.println("1) " + unico.getApellido() + ", " + unico.getNombre()
                    + " - " + unico.getTipo_documento() + " " + unico.getNroDocumento());
        }

        // Si hay varios resultados 
        if (encontrados.size()>1){
            System.out.println("Se encontraron varios huéspedes:");
            for (int i = 0; i < encontrados.size(); i++) {
                HuespedDTO h = encontrados.get(i);
                System.out.printf("%d) %s, %s - %s %s%n",
                      i + 1, h.getApellido(), h.getNombre(), h.getTipo_documento(), h.getNroDocumento());
            }
        }

        System.out.print("Seleccione número para modificar o presione ENTER sin elegir para dar de alta uno nuevo: ");
        String seleccion = sc.nextLine().trim();
        

        if (seleccion.isEmpty()) {

            System.out.println("Sin selección. Ejecutando CU9 (Dar de alta huésped).");
            System.out.println("Rellene todos los campos para generar un huesped, los campos EMAIL, CUIT y POSICIONIVA no son obligatorios");
            HuespedDTO nuevoH = new HuespedDTO();
            PersonaFisicaDTO nuevoP = new PersonaFisicaDTO(nuevoH);
            do{
                mostrarDatos(nuevoH, nuevoP);
                String indice;
                String seleccionar;
                System.out.print("INDICE: ");
                indice = sc.nextLine();
                seleccionar = sc.nextLine();
                setElemento(nuevoP, nuevoH, seleccionar, Integer.parseInt(indice));
            }while(!verificarCamposObligatoriosIncompletos(nuevoH, false) || !verificarCamposObligatoriosIncompletos(nuevoP));
            darDeAltaHuesped(nuevoH, nuevoP);
        }
        try {
            int indice = Integer.parseInt(seleccion) - 1;
            if (indice < 0 || indice >= encontrados.size()) {
                System.out.println("Selección inválida.");
            }

            HuespedDTO elegido = encontrados.get(indice);
            System.out.println("Huésped seleccionado: "
                    + elegido.getApellido() + ", " + elegido.getNombre());
            //return elegido;
            // EJECUTAR CU10 MODIFICAR HUESPED
            PersonaFisicaDAO perDao = new PersonaFisicaDAOtxt();
            PersonaFisicaDTO p = perDao.buscarPorDocumento(elegido.getTipo_documento(),elegido.getNroDocumento()); //se trabajaba directamente con TipoDoc y nroDoc (si no se cargan se rompe)
            
            modificarHuesped(elegido, p);
            

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }

        
    }

    // FUNCIONES AUXILIARES

    public static void mostrarDatos(HuespedDTO h, PersonaFisicaDTO p) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("1- " + (h.getApellido() != "" ? h.getApellido() : "FALTA APELLIDO"));
        System.out.println("2- " + (h.getNombre() != "" ? h.getNombre() : "FALTA NOMBRE"));
        System.out.println("3- " + (h.getTipo_documento() != null ? h.getTipo_documento() : "FALTA TIPO DOC") + " TIPODOC DNI POR DEFECTO");
        System.out.println("4- " + (h.getNroDocumento() != "" ? h.getNroDocumento() : "FALTA NRO DOC"));
        System.out.println("5- " + (p.getCUIT() != "" ? p.getCUIT() : "FALTA CUIT"));
        System.out.println("6- " + (p.getPosicionIVA() != "" ? p.getPosicionIVA() : "FALTA POSICION IVA(NO OBLIGATORIO)"));
        System.out.println("7- " + (h.getFechaDeNacimiento() != null ? formatter.format(h.getFechaDeNacimiento()) : "FALTA FECHA DE NACIMIENTO"));
        System.out.println("8- " + (h.getTelefono() != "" ? h.getTelefono() : "FALTA TELEFONO"));
        System.out.println("9- " + (h.getOcupacion() != "" ? h.getOcupacion() : "FALTA OCUPACION"));
        System.out.println("10- " + (h.getEmail() != "" ? h.getEmail() : "FALTA EMAIL(NO OBLIGATORIO)"));
        System.out.println("11- " + (h.getNacionalidad() != "" ? h.getNacionalidad() : "FALTA NACIONALIDAD"));
        System.out.println("12- " + h.isAlojado() + " (si se alojó)");
    }

    public static void setElemento(PersonaFisicaDTO p, HuespedDTO hdto, String elemento, int indice) {
        switch (indice) {
            case 1: // apellido
                hdto.setApellido(elemento);
                break;
            case 2: // nombre
                hdto.setNombre(elemento);
                break;
            case 3: // tipo documento
                hdto.setTipo_documento(TipoDoc.valueOf(elemento.toUpperCase()));
                break;
            case 4: // nro documento
                hdto.setNroDocumento(elemento);
                break;
            case 5: // CUIT
                if(elemento.equals("")){
                    if(!(p.getPosicionIVA().equals("")) && !p.getPosicionIVA().equals("Responsable Inscripto")){
                        p.setCUIT("Factura Tipo B");
                    }
                }else{
                    p.setCUIT(elemento);
                }
                break;
            case 6: // posicion IVA
                if ((elemento.equals(""))) {
                    p.setPosicionIVA("Consumidor final");
                } else
                    p.setPosicionIVA(elemento);
                break;
            case 7: // fecha nacimiento
                hdto.setFechaDeNacimiento(elemento);
                break;
            case 8: // telefono
                hdto.setTelefono(elemento);
                break;
            case 9: // ocupacion
                hdto.setOcupacion(elemento);
                break;
            case 10: // email
                hdto.setEmail(elemento);
                break;
            case 11: // nacionalidad
                hdto.setNacionalidad(elemento);
                break;

        }
    }

    public static boolean verificarCamposObligatoriosIncompletos(PersonaFisicaDTO p) {
        boolean verifica = true;
        if (p.getCUIT().equals("")) {
            System.out.println("ERROR COMPLETAR CUIT:");
            verifica = false;
        }

        return verifica;
    }

    // apellido nombre tipo y nro doc fecha nacimiento direccion telefono ocupacion
    // nacionalidad son obligatorios
    public static boolean verificarCamposObligatoriosIncompletos(HuespedDTO h, boolean modificaDNI) {
        boolean verifica = true;
        if (h.getApellido().equals("") || h.getNombre().equals("") ||
                h.getTipo_documento().toString().isEmpty() || h.getNroDocumento().equals("") ||
                (h.getFechaDeNacimiento() == null || h.getFechaDeNacimiento().toString().equals("")) || h.getTelefono().equals("") ||
                h.getOcupacion().equals("") || h.getNacionalidad().equals("")) {
            System.out.println("ERROR COMPLETAR CAMPOS:");
            verifica = false;
        }

        if (h.getApellido().equals("")) {
            System.out.println("COMPLETAR APELLIDO");
        }

        if (h.getNombre().equals("")) {
            System.out.println("COMPLETAR nombre");
        }

        if (h.getTipo_documento().toString().equals("")) {
            System.out.println("COMPLETAR tipo doc ");
        }

        if (h.getNroDocumento().equals("")) {
            System.out.println("COMPLETAR Nro doc ");
        }

        if (h.getFechaDeNacimiento()==null || h.getFechaDeNacimiento().toString().equals("")) {
            System.out.println("COMPLETAR Fecha nacimiento ");
        }

        if (h.getTelefono().equals("")) {
            System.out.println("COMPLETAR Telefono ");
        }

        if (h.getOcupacion().equals("")) {
            System.out.println("COMPLETAR Ocupacion ");

        }

        if (h.getNacionalidad().equals("")) {
            System.out.println("COMPLETAR nacionalidad ");
        }

        if (!h.getNroDocumento().equals("") && !h.getTipo_documento().toString().equals("") && modificaDNI) {

            HuespedDAO dao = new HuespedDAOtxt();
            HuespedDTO t = dao.buscarHuesped(h.getTipo_documento(), h.getNroDocumento());

            if (t != null && !t.toString().equals(h.toString())) {
                System.out.println("¡CUIDADO! El tipo y número de documento ya existen en el sistema");
                System.out.println("ACEPTAR IGUALMENTE -> A | MODIFICAR -> M");
                // agregame entrada usando scanner
                Scanner scanner = new Scanner(System.in);

                String entrada = scanner.nextLine();
                while (!entrada.toLowerCase().equals("a") && !entrada.toLowerCase().equals("m")) {
                    System.out.println(
                            "Entrada inválida. Por favor ingrese 'A' para ACEPTAR IGUALMENTE o 'M' para MODIFICAR.");
                    entrada = scanner.nextLine();
                }
                scanner.close();
                if (!entrada.toLowerCase().equals("a")) { // si aprierto A verifica tiene el valor original, sino se
                                                          // suma un error
                    verifica = false;
                }
            }
        }
        return verifica;

    }

}