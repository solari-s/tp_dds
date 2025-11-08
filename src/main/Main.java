package main;

import java.util.Scanner;

import conserje.ConserjeDAOImplementacion;
import gestores.GestorConserje;
import gestores.GestorHuesped;
import huesped.HuespedDAO;
import huesped.HuespedDAOtxt;

public class Main {

    static ConserjeDAOImplementacion conserjeDAO = new ConserjeDAOImplementacion();
    static GestorConserje gestorConserje = new GestorConserje(conserjeDAO);
    static HuespedDAO huespedDAO = new HuespedDAOtxt();
    static GestorHuesped gestorHuesped = new GestorHuesped(huespedDAO);

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Ingrese contrasenia: ");
        String contrasenia = sc.nextLine();

        boolean autenticado = gestorConserje.autentificarConserje(usuario.trim(), contrasenia.trim());

        if (!autenticado) {
            System.out.println("Autenticacion fallida. Saliendo del sistema.");
            sc.close();
        } else {
            System.out.print("Bienvenido " + usuario + "! Seleccione una de las opciones: ");
            menu(sc, gestorHuesped);
            sc.close();
        }

    }

    public static void menu(Scanner sc, GestorHuesped gestorHuesped) {
        int opcion;

        do {
            System.out.println("\n----------Menu Principal----------");
            System.out.println("1. Buscar Huesped");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.println("--- Opción 1: Buscar Huésped ---");
                        gestorHuesped.ejecutarCU2();
                        break;
                    case 0:
                        System.out.println("Cerrando sesión...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número.");
                opcion = -1;
            }

        } while (opcion != 0);
    }

}
