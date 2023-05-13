package pl.isa.javasmugglers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu {

    private static ArrayList<String> zajecia = new ArrayList<String>();
    private static ArrayList<String> listaProfesorow = new ArrayList<String>();
    private static String FILENAME = "plan-zajec.json";
    private static final String USERSDATABASE = "allUsersDatabase.json";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        pokazListeProfesorow();
        Scanner scanner = new Scanner(System.in);
        int wybor = 0;
        while (wybor != 4) {
            System.out.println("Witaj Studenciee! Wybierz opcję:");
            System.out.println("1. Pokaż listę profesorów -wybierz profesora");
            System.out.println("2. Pokaż zajęcia");
            System.out.println("3. Wypisz się z zajęć");
            System.out.println("4. Wyjdź");
//ok
            wybor = scanner.nextInt();
            String s = scanner.nextLine();

            switch (wybor) {
                case 1 -> pokazListeProfesorow();
                case 2 -> pokazZajecia(scanner);//pobieranie zajęć z pliku
                case 3 -> WypiszSieZZajec(scanner);
                case 4 -> zapiszDoPliku();
                default -> System.out.println("Nieprawidłowy wybór!");
            }
        }
        scanner.close();
    }

    //ok
    private static void pokazListeProfesorow() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERSDATABASE))) {
            String json = reader.readLine();
            if (json != null && !json.isEmpty()) {
                listaProfesorow = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
                }.getType());
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku " + USERSDATABASE);
        }
    }
// Nie wiem czy takie podejście jest ok? Wydaje mi się że wybierać się powinno tylko zajęcia a nie profesora

    private static void zapiszDoPliku() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            String json = gson.toJson(zajecia);
            writer.write(json);
            System.out.println("Dane zostały zapisane do pliku " + FILENAME);
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku " + FILENAME);

        }
    }

    private static void pokazZajecia(Scanner scanner) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String json = reader.readLine();
            if (json != null && !json.isEmpty()) {
                FILENAME = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
                }.getType());
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku " + USERSDATABASE);
            }
        }

    /*public static void
        pokazListeProfesorow();
        Scanner scanner = new Scanner(System.in);
        int wybor = 0;
        while (wybor != 4) {
            System.out.println(" Wybierz opcję:");
            System.out.println("1. Pokaż listę profesorów -wybierz profesora");
            System.out.println("2. Pokaż zajęcia");
            System.out.println("3. Wypisz się z zajęć");
            System.out.println("4. Wyjdź");
    */





    private static void WypiszSieZZajec(Scanner scanner) {
        if (zajecia.isEmpty()) {
            System.out.println("Nie ma żadnych zaplanowanych zajęć.");
        } else {
            System.out.println("Które zajęcia chcesz usunąć?");
            pokazZajecia(scanner);

            int numer = scanner.nextInt();
            scanner.nextLine();

            if (numer > 0 && numer <= zajecia.size()) {
                String nazwa = zajecia.remove(numer - 1);
                System.out.println("Zajęcia " + nazwa + " zostały usunięte z planu.");
            } else {
                System.out.println("Nieprawidłowy numer zajęć!");
            }
        }
    }

}
