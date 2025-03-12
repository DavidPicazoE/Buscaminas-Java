/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package picazo_david_projecte_uf2_pt1;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author david
 */
public class util {

    /**
     * @param args the command line arguments
     *
     */
    
    public static void main(String[] args) {
    }

    public static double pedirDouble(Scanner scanner, String mensaje) {
        double numero = 0;
        boolean entradaValida = false;

        do {
            try {
                System.out.print(mensaje);
                numero = scanner.nextDouble();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número decimal.");
                // Limpiar el buffer del scanner en caso de entrada no válida
                scanner.nextLine();
            }
        } while (!entradaValida);

        return numero;
    }

    public static int pedirEntero(Scanner scanner, String mensaje) {
        int numero = 0;
        boolean entradaValida = false;

        do {
            try {
                System.out.print(mensaje);
                numero = scanner.nextInt();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero.");
                // Limpiar el buffer del scanner en caso de entrada no válida
                scanner.nextLine();
            }
        } while (!entradaValida);

        return numero;
    }

    public static String pedirString(Scanner scanner, String mensaje) {
        String input = "";
        boolean entradaValida = false;

        do {
            try {
                System.out.print(mensaje);
                input = scanner.nextLine();
                input = input.toLowerCase();
                if (input.equals("si") || input.equals("s") || input.equals("no") || input.equals("n")) {
                    entradaValida = true;
                } else {
                    entradaValida = false;
                }
                // Si llega aquí, la entrada es válida.
            } catch (Exception e) {
                System.out.println("Error: Has d'escriure si o no.");
                // En caso de error, limpiar el buffer del scanner
                scanner.nextLine();
            }
        } while (!entradaValida);

        return input;
    }
    


}
