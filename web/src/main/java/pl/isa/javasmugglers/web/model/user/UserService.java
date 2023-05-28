package pl.isa.javasmugglers.web.model.user;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    public String signUpUser(User user){
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
       String password = user.getPassword();
        user.setPassword(password);

        userRepository.save(user);

            // TODO:SEND CONFIRMATION And tokken
        return "it works";
    }
}
