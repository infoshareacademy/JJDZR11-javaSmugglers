package pl.isa.javasmugglers;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuAddUser {
    private String userType;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;



    //Metoda zbierająca input użytkownika
    public void addUser() {

        //Selekcja typu użytkownika
        System.out.println("Proszę wybrać po przez wpisanie odpowiedniej liczby: \n" + "1. Uczniem \n" + "2. Profesorem: ");
        if (getValidInput() == 1) this.userType = "Uczeń";
        else this.userType = "Profesor";

        Scanner scanner = new Scanner(System.in);
        //Podanie przez użytkownika imienia
        System.out.print("Podaj swoje imie: ");
        this.userFirstName = scanner.nextLine();

        //Podanie przez użytkownika nazwiska
        System.out.print("Podaj swoje nazwisko: ");
        this.userLastName = scanner.nextLine();

        //podanie przez użytkownika emaila
        System.out.print("Podaj swój email: ");
        this.userEmail = scanner.nextLine();

        //podanie przez użytkownika hasła
        System.out.print("Podaj swoje hasło: ");
        this.userPassword = scanner.nextLine();

        DatabaseWriter newUser = new DatabaseWriter();
        newUser.addUserToDatabase(this.userType, this.userFirstName, this.userLastName,  this.userEmail, this.userPassword);

    }

    //Test inputu przy wyborze typu użytkownika
    public int getValidInput() {
        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                userInput = scanner.nextInt();
                if (userInput >= 1 && userInput <= 2) {
                    isValid = true;
                } else {
                    System.out.println("Nieprawidłowa liczba. Podaj liczbę od 1 do 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowe dane wejściowe. Oczekiwano liczby.");
                scanner.nextLine();
            }
        }
        return userInput;
    }
}

