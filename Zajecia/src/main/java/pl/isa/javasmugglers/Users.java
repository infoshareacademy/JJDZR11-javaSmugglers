package pl.isa.javasmugglers;

import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Users {
    private String userType;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;

    //Metoda zbierająca input użytkownika
    public void userInput() {


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

        System.out.print("Podaj swoje hasło: ");
        this.userPassword = scanner.nextLine();
    }

    // Metoda dodająca nowego użytkownika
    public void addUser() {

        //tworzenie pliku  użytkownika do którego zapisywana będzie lista
        try (FileWriter file = new FileWriter("src/main/java/pl/isa/javasmugglers/database/userfiles/" + this.userEmail + ".json")) {
            System.out.println("Plik użytkownika został stworzony poprawnie");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dodanie użytkownika do bazy danych użytkowników
        //JSONObject tworzy obiekt, który za pomocą FileWriter zapisujemy do pliku, w tym przypadku dopisujemy do pliku już istniejącego (jeśli przy pierwszym zapisie plik nie istnieje filewriter go stworzy)
        JSONObject userObject = new JSONObject();
        userObject.put("userType", this.userType);
        userObject.put("email", this.userEmail);
        userObject.put("firstName", this.userFirstName);
        userObject.put("lastName", this.userLastName);
        userObject.put("password", this.userPassword);

        try (FileWriter file = new FileWriter("src/main/java/pl/isa/javasmugglers/database/" + "allUsersDatabase.json", true)) {
            file.write(userObject.toJSONString());
            file.write("\n");
            System.out.println("Informacje o użytkowniku zostały poprawnie dodane do bazy danych");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
