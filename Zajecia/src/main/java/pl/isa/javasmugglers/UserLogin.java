package pl.isa.javasmugglers;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.*;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.JsonMappingException;

public class UserLogin {
    
    
    public class LoginStudent {

        public static User loginStudent() {
            int i = 0;
            String userEmail = null;
            String userPassword = null;
            String userType;
            List<User> userListList = new ArrayList<>();
            try {
                byte[] mapData = Files.readAllBytes(Paths.get("Zajecia/src/main/Resources/allUsersDatabase.json"));
                User[] userArr = null;

                ObjectMapper objectMapper = new ObjectMapper();
                userArr = objectMapper.readValue(mapData, User[].class);
                userListList = Arrays.asList(userArr);
                User user = new User();
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nPodaj e-mail:");
                userEmail = scanner.nextLine();
                System.out.println("\nPodaj hasło:");
                userPassword = scanner.nextLine();
                userType = "Uczeń";
                for (User user1 : userListList) {
                    if ((userEmail.equals(user1.getUserEmail()) && userPassword.equals(user1.getUserPassword())) && userType.equals(user1.getUserType())) {

                        i = userListList.indexOf(user1);
                        System.out.println("Zalogowano jako " + user1.getUserFirstName() + " " + user1.getUserLastName());
                        break;
                    }


                }

            } catch (JsonMappingException ex) {
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            User checkData = new User();
            checkData.setUserType(userListList.get(i).getUserType());
            checkData.setUserPassword(userPassword);
            checkData.setUserEmail(userEmail);
            checkData.setUserLastName(userListList.get(i).getUserLastName());
            checkData.setUserFirstName(userListList.get(i).getUserFirstName());

            if (!userListList.contains(checkData)){
                System.out.println("Błędne dane logowania.");
            }
            return userListList.get(i);
        }
    }

    public class LoginProfessor {
        public static User loginProfessor() {
            List<User> userListList = new ArrayList<>();
            int i = 4;
            try {
                byte[] mapData = Files.readAllBytes(Paths.get("Zajecia/src/main/Resources/allUsersDatabase.json"));
                User[] userArr = null;

                ObjectMapper objectMapper = new ObjectMapper();
                userArr = objectMapper.readValue(mapData, User[].class);
                userListList = Arrays.asList(userArr);
                User user = new User();
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nPOdaj e-mail:");
                String userEmail = scanner.nextLine();
                System.out.println("\nPodaj hasło:");
                String userPassword = scanner.nextLine();
                String userType = "Profesor";
                for (User user1 : userListList) {
                    if ((userEmail.equals(user1.getUserEmail()) && userPassword.equals(user1.getUserPassword())) && userType.equals(user1.getUserType())) {
                        System.out.println("Zalogowano jako " + user1.getUserFirstName() + " " + user1.getUserLastName());
                        i = userListList.indexOf(user1);
                        break;

                    }


                }
                int j = 0;
                for (User user2 : userListList){
                    if (!userListList.contains(userListList.get(i))){
                        j++;
                        if (j == userListList.size()){
                            System.out.println("Błędne dane uzytkownika");
                        }
                    }
                }

            } catch (IOException ex) {

                ex.printStackTrace();
            }

            return userListList.get(i);
        }
    }
}