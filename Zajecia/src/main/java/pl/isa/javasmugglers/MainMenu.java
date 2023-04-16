package pl.isa.javasmugglers;

public class MainMenu {

    // Metoda wyświetlająca menu w konsoli
    public String getMenu() {
        return  "\nCo chcesz zrobić?: \n" +
                "1. Stworzyć konto użytkownika. \n" +
                "2. Zalogować się na konto ucznia. \n" +
                "3. Zalogować się na konto profesora.\n" +
                "4. Wyjście z programu." +
                "\nProszę podać odpowiedni numer: ";
    }

    // Metoda sprawdzająca input w menu głównym
    public int getValidInput() {
        InputValidator inputValidator = new InputValidator();
        return inputValidator.getValidInput( 4);
    }


    //metoda odpalająca odpowiednie klasy w zależności od inputu użytkownika
    public void userSelection(int userSelection) {
        switch (userSelection) {
            case 1:
                //Tutaj wywołuję swoją metodę Błażej
                MenuAddUser newUser = new MenuAddUser();
                newUser.addUser(this);
                break;
            case 2:
                //Tutaj wywołują swoje metody Mikołaj i Patryk
                System.out.print("Wybrano 2");
                break;
            case 3:
                //Tutaj wywołuje swoją metodę Dominik
                System.out.print("Wybrano 3");
                break;
            case 4:
                //Możliwość wyjścia z programu
                System.out.println("Dziękujemy. Trwa wyłączanie programu....");
                System.exit(0);
                break;
        }
    }

}
