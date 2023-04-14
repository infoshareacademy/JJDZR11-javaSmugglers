package pl.isa.javasmugglers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}

