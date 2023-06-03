package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class SecurityController {
    private final UserRepository userRepository;
    @RequestMapping(value = "/succeslogin.html", method = RequestMethod.GET)
    @ResponseBody
    public Long currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        return userRepository.findByEmail(principal.getName()).get().getId();
    }
}