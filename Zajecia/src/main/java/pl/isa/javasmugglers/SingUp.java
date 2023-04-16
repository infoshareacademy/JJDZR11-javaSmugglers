package pl.isa.javasmugglers;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
public class SingUp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        {

        throws{
            File file = new File("test.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                System.out.println(sc.nextLine());

            public void displayUsersByType(String userType) {
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
            }



        }
        }
    }
}