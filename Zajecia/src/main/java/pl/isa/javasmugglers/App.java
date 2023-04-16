package pl.isa.javasmugglers;


public class App {
    public static void main(String[] args) {

        System.out.println("WITAMY W MENAGERZE ZAJĘĆ");

        MainMenu mainMenu = new MainMenu();
        boolean isOn = true;
        while (isOn) {
            System.out.println(mainMenu.getMenu());
            mainMenu.userSelection(mainMenu.getValidInput());
        }
    }
}

