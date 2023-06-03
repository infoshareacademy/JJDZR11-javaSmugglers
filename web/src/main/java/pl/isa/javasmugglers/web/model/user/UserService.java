package pl.isa.javasmugglers.web.model.user;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public String signUpUser(User user) {
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
        return "Użytkownik  " + user.getFirstName() + " " + user.getLastName() + " został zarejestrowany. Proszę czekać na akceptacje użytkownika.";
    }
    private static final String USER_NOT_FOUND = "uSER WITH EMAIL %S NOT FOUND.";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
        new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
}
