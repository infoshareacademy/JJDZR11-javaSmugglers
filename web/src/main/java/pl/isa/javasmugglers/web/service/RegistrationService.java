package pl.isa.javasmugglers.web.service;

import pl.isa.javasmugglers.web.model.registration.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserStatus;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserValidator userValidator;

    public String register(User request) {
        boolean isValidUser = userValidator.test(request.getEmail());
        if (!isValidUser) {
            throw new IllegalStateException("user has been rejected");
        }
        return userService.save(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail().toLowerCase(),
                        request.getPassword(),
                        UserStatus.WAITING_FOR_CONFIRMATION,
                        request.getType(),
                        userService.generateAuthToken()

                      )
        );
    }
}
