package pl.isa.javasmugglers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {


    public int getValidInput(int maxInputNumber) {
        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                userInput = scanner.nextInt();
                if (userInput >= 1 && userInput <= maxInputNumber) {
                    isValid = true;
                } else {
                    System.out.println("Nieprawidłowa liczba. Podaj liczbę od 1 do " + maxInputNumber + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowe dane wejściowe. Oczekiwano liczby.");
                scanner.nextLine();
            }
        }
        return userInput;
    }
}
