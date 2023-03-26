package pl.isa.javasmugglers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    // Metoda wyświetlająca menu w konsoli
    public String getMenu() {
        return "WITAMY W MENAGERZE ZAJĘĆ \n" +
                "\nCo chcesz zrobić?: \n" +
                "1. Stworzyć konto użytkownika. \n" +
                "2. Zalogować się na konto ucznia. \n" +
                "3. Zalogować się na konto profesora." +
                "\nProszę podać odpowiedni numer: ";
    }

    // Metoda sprawdzająca input w menu głównym
    public int getValidInput() {
        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                userInput = scanner.nextInt();
                if (userInput >= 1 && userInput <= 3) {
                    isValid = true;
                } else {
                    System.out.println("Nieprawidłowa liczba. Podaj liczbę od 1 do 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowe dane wejściowe. Oczekiwano liczby.");
                scanner.nextLine();
            }
        }

        return userInput;
    }


    //metoda odpalająca odpowiednie klasy w zależności od inputu użytkownika
    public void userSelection(int userSelection) {
        switch (userSelection) {
            case 1:
                //Tutaj wywołuję swoją metodę Błażej
                MenuAddUser newUser = new MenuAddUser();
                newUser.addUser();
                break;
            case 2:
                //Tutaj wywołują swoje metody Mikołaj i Patryk
                System.out.print("Wybrano 2");
                break;
            case 3:
                //Tutaj wywołuje swoją metodę Dominik
                System.out.print("Wybrano 3");
                break;
        }
    }

}
