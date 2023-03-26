package pl.isa.javasmugglers;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Wyświetlenie menu
            System.out.println("Wybierz opcję:");
            System.out.println("1. Wczytaj listę zajęć z pliku .json");
            System.out.println("2. Wyjście");

            // Wczytanie wyboru użytkownika
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Wczytanie listy zajęć z pliku .json
                    Gson gson = new Gson();
                    try {
                        Schedule schedule = gson.fromJson(new FileReader("/home/dominik/Documents/JJDZR11-javaSmugglers/Zajecia/src/main/resources/schedule.json"),
                                // TO DO: dodanie zaciągania danych .json z maila z pliku Błażeja
                                Schedule.class);
                        List<Course> courses = schedule.getCourses();
                        for (Course course : courses) {
                            System.out.println(course.getName1() + " " + course.getTime() + " " + course.getRoom());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 2:
                    // Zakończenie programu
                    System.out.println("Do widzenia!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Niepoprawny wybór.");
                    break;
            }
        }
    }
}

// Klasa reprezentująca harmonogram zajęć
class Schedule {
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }
}

// Klasa reprezentująca pojedyncze zajęcia
class Course {
    private String name;
    private String time;
    private String room;

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public String getName1() {
        return name;
    }
}

