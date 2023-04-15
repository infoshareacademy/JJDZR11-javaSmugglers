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
    public void addUser(MainMenu mainMenu) {

        InputValidator inputValidator = new InputValidator();


        //Selekcja typu użytkownika
        System.out.println("Proszę wybrać poprzez wpisanie odpowiedniej liczby: \n" + "1. Uczniem \n" + "2. Profesorem: ");
        if (inputValidator.getValidInput(2) == 1) this.userType = "Uczeń";
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

        System.out.println();
        System.out.println("Co chcesz teraz zrobić:");
        System.out.println("1. Dodaj kolejnego użytkownika.");
        System.out.println("2. Powrót do menu głównego.");
        System.out.println("3. Wyjście z programu.");
        System.out.print("Proszę wybrać opcję: ");

        switch (inputValidator.getValidInput(3)) {
            case 1:
                addUser(mainMenu);
                break;
            case 2:
                System.out.println(mainMenu.getMenu());
                mainMenu.userSelection(mainMenu.getValidInput());
                break;
            case 3:
                System.out.println("Dziękujemy. Trwa wyłączanie programu....");
                System.exit(0);
                break;
        }
    }

}

