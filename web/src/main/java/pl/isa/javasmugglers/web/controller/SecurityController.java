package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class SecurityController {
    private final UserRepository userRepository;


    @RequestMapping(value = "/succeslogin.html", method = RequestMethod.GET)
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal()    ;
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Long id = user.getId();
        if (user.getType() == UserType.STUDENT) {
            return "redirect:/user-dashboard/" + id;
        } else if (user.getType() == UserType.PROFESOR) {
            return "redirect:/DashboardProfessor/" + id;
        } else {
            throw new RuntimeException("Unexpected user type");
        }
    }
}