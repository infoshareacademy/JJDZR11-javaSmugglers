package pl.isa.javasmugglers.web.model.registration;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserService;
import pl.isa.javasmugglers.web.model.user.UserType;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserValidator userValidator;
    public String register(RegistrationRequest request) {
        boolean isValidUser = userValidator.test(request.getEmail());
        if (!isValidUser){
            throw new IllegalStateException("user has been rejected");
        }
        return userService.signUpUser(
        new User(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserType.STUDENT)
        );
    }
}
