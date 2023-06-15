package pl.isa.javasmugglers.web.service;

import pl.isa.javasmugglers.web.model.registration.RegistrationRequest;
import pl.isa.javasmugglers.web.model.registration.UserValidator;
import pl.isa.javasmugglers.web.model.user.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserValidator userValidator;

    public String register(RegistrationRequest request) {
        boolean isValidUser = userValidator.test(request.getEmail());
        if (!isValidUser) {
            throw new IllegalStateException("user has been rejected");
        }
        return userService.signUpUser(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getType(),
                        AccountsStatus.ACTIVE)
        );
    }
}
