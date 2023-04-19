package pl.isa.javasmugglers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static ArrayList<String> zajecia = new ArrayList<String>();
    private static final String FILENAME = "plan-zajec.json";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        wczytajZPliku();
        Scanner scanner = new Scanner(System.in);
        int wybor = 0;
        while (wybor != 4) {
            System.out.println("Witaj Profesorze! Wybierz opcję:");
            System.out.println("1. Pokaż plan zajęć");
            System.out.println("2. Dodaj zajęcia");
            System.out.println("3. Usuń zajęcia");
            System.out.println("4. Wyjdź");

            wybor = scanner.nextInt();
            scanner.nextLine(); // Ignoruj znak nowej linii

            switch (wybor) {
                case 1:
                    pokazZajecia();
                    break;
                case 2:
                    dodajZajecia(scanner);
                    break;
                case 3:
                    usunZajecia(scanner);
                    break;
                case 4:
                    zapiszDoPliku();
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór!");
            }
        }
        scanner.close();
    }

    private static void wczytajZPliku() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String json = reader.readLine();
            if (json != null && !json.isEmpty()) {
                zajecia = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
                }.getType());
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku " + FILENAME);
        }
    }

    private static void zapiszDoPliku() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            String json = gson.toJson(zajecia);
            writer.write(json);
            System.out.println("Dane zostały zapisane do pliku " + FILENAME);
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku " + FILENAME);
        }
    }

    private static void pokazZajecia() {
        if (zajecia.isEmpty()) {
            System.out.println("Nie ma żadnych zaplanowanych zajęć.");
        } else {
            System.out.println("Plan zajęć:");
            for (int i = 0; i < zajecia.size(); i++) {
                System.out.println((i + 1) + ". " + zajecia.get(i));
            }
        }
    }

    private static void dodajZajecia(Scanner scanner) {
        System.out.println("Podaj nazwę zajęć:");
        String nazwa = scanner.nextLine();
        zajecia.add(nazwa);
        System.out.println("Zajęcia " + nazwa + " zostały dodane do planu.");
    }


    private static void usunZajecia(Scanner scanner) {
        if (zajecia.isEmpty()) {
            System.out.println("Nie ma żadnych zaplanowanych zajęć.");
        } else {
            System.out.println("Które zajęcia chcesz usunąć?");
            pokazZajecia();

            int numer = scanner.nextInt();
            scanner.nextLine(); // Ignoruj znak nowej linii

            if (numer > 0 && numer <= zajecia.size()) {
                String nazwa = zajecia.remove(numer-1);
                System.out.println("Zajęcia " + nazwa + " zostały usunięte z planu.");
            } else {
                System.out.println("Nieprawidłowy numer zajęć!");
            }
        }
    }

}
