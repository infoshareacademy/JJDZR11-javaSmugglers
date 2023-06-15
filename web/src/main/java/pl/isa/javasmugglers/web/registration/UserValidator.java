package pl.isa.javasmugglers.web.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class UserValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // TODO: to validate user
        return true;
    }
}
