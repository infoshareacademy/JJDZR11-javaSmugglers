package pl.isa.javasmugglers;

import java.io.File;

public class StudentMenu {

    // Metoda pokazująca menu ucznia w konsoli
    public String getMenu() {
        return "\nCo chcesz zrobić?: \n" +
                "1. Znajdż Profesora \n" +
                "2. Wyloguj się. \n" +
                "3. Powrót do głównego menu.\n" +
                "\nProszę podać odpowiedni numer: ";
    }
}






























//    // Metoda sprawdzająca input w menu głównym
//    public <InputValidator> int getValidInput() {
//        InputValidator inputValidator = new InputValidator();
//        return inputValidator.getClass(3);
//    }
//
//    public String loginUserP() {
//        String string2 = UserLogin.LoginProfessor.loginProfessor().toString();
//        File userFile2 = new File("src/main/Resources/UserFiles/" + string2 + ".json");
//        return userFile2.toString();
//    }
//
//    public String loginUserU() {
//        String string1 = UserLogin.LoginStudent.loginStudent().toString();
//        File userFile1 = new File("src/main/Resources/UserFiles/" + string1 + ".json");
//        return userFile1.toString();
//    }
//
//    //metoda odpalająca odpowiednie klasy w zależności od inputu użytkownika
//    public void userSelection(int userSelection) {
//        switch (userSelection) {
//            case 1: {
//                //Tutaj wywołuję swoją metodę Błażej
//                MenuAddUser newUser = new MenuAddUser();
//                newUser.addUser(this);
//                break;
//            }
//            case 2: {
//
//                System.out.print("Wybrano 2");
//                final String uczenPathName = loginUserU();
//                System.out.println(uczenPathName);
//                break;
//            }
//            case 3: {
//
//                System.out.print("Wybrano 3");
//                final String profesorPathName = loginUserP();
//                System.out.println(profesorPathName);
//                break;
//            }
//            case 4:
//                //Możliwość wyjścia z programu
//                System.out.println("Dziękujemy. Trwa wyłączanie programu....");
//                System.exit(0);
//                break;
//        }
    }

}