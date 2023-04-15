package pl.isa.javasmugglers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseWriter {

    // Metoda, która dodaje użytkownika do bazy danych
    public void addUserToDatabase(String userType, String userFirstName, String userLastName, String userEmail, String userPassword) {

        try {
            File userFile = new File("src/main/Resources/UserFiles/" + userEmail + ".json");
            if (!userFile.exists()) {
                userFile.createNewFile();
            }
            System.out.println("Plik użytkownika został stworzony poprawnie");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dodanie użytkownika do bazy danych użytkowników
        try {
            File userDatabase = new File("src/main/Resources/" + "allUsersDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> userList;
            if (userDatabase.exists()) {
                userList = objectMapper.readValue(userDatabase, new TypeReference<>() {
                });
            } else {
                userList = new ArrayList<>();
            }
            User newUser = new User(userType, userFirstName, userLastName, userEmail, userPassword);
            userList.add(newUser);
            objectMapper.writeValue(userDatabase, userList);
            System.out.println("Informacje o użytkowniku zostały poprawnie dodane do bazy danych");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// kod testowy do wyświetlania odpowiednich użytkowników
/*    public void displayUsersByType(String userType) {
        try {
            File userDatabase = new File("src/main/Resources/" + "allUsersDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();

            // Zakładamy, że baza danych zawsze istnieje
            List<User> userList = objectMapper.readValue(userDatabase, new TypeReference<>() {});

            List<User> filteredUserList = userList.stream()
                    .filter(user -> user.getUserType().equalsIgnoreCase(userType))
                    .toList();

            System.out.println("Lista użytkowników typu \"" + userType + "\":");
            for (User user : filteredUserList) {
                System.out.println("Imię: " + user.getUserFirstName() +
                        ", Nazwisko: " + user.getUserLastName() +
                        ", Email: " + user.getUserEmail());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

