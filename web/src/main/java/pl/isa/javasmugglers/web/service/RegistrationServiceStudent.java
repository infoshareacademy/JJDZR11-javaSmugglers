package pl.isa.javasmugglers.web.service;

import pl.isa.javasmugglers.web.registration.RegistrationRequest2;
import pl.isa.javasmugglers.web.registration.UserValidator;
import pl.isa.javasmugglers.web.model.user.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;

@Service
@AllArgsConstructor
public class RegistrationServiceStudent {

    private final UserService userService;
    private final UserValidator userValidator;

    public String register(RegistrationRequest2 request) {
        boolean isValidUser = userValidator.test(request.getEmail());
        if (!isValidUser) {
            throw new IllegalStateException("user has been rejected");
        }
        return userService.signUpUser(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserType.STUDENT,
                        AccountsStatus.PENDING)
        );
    }
}
