package pl.isa.javasmugglers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class MenuStudent {

    private static ArrayList<String> zajecia = new ArrayList<String>();
    private static final String FILENAME = "plan-zajec.json";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        wczytajZPliku();
        Scanner scanner = new Scanner(System.in);
        int wybor = 0;
        while (wybor != 3) {
            System.out.println("Witaj Studencie! Wybierz opcję:");
            System.out.println("1. Wybierz Profesora");
            System.out.println("2.Wybierz zajęcia");
            System.out.println("3. Wyjdź");

            wybor = scanner.nextInt();
            scanner.nextLine(); // Ignoruj znak nowej linii

            switch (wybor) {
                case 1:
                    pokazProfesora();
                    break;
                case 2:
                    wybierzZajęciar);
                    break;
                case 3:
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

    private static void pokazProfesora() {
        if (zajecia.isEmpty()) {
            System.out.println("Nie ma żadnych zaplanowanych zajęć.");
        } else {
            System.out.println("Plan zajęć:");
            for (int i = 0; i < zajecia.size(); i++) {
                System.out.println((i + 1) + ". " + zajecia.get(i));
            }
        }
    }

    private static void dodajProfesora(Scanner scanner) {
        System.out.println("Podaj nazwę zajęć:");
        String nazwa = scanner.nextLine();
        zajecia.add(nazwa);
        System.out.println("Zajęcia " + nazwa + " zostały dodane do planu.");
    }
    }