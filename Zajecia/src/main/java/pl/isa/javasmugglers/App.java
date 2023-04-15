package pl.isa.javasmugglers;


public class App {
    public static void main(String[] args) {
        //testowe odpalenie aplikacji
        System.out.println("WITAMY W MENAGERZE ZAJĘĆ");
        MainMenu mainMenu = new MainMenu();
        while (true) {
            System.out.println(mainMenu.getMenu());
            mainMenu.userSelection(mainMenu.getValidInput());
        }
    }
}
