package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserStatus;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class SecurityController {
    private final UserRepository userRepository;


    @RequestMapping(value = "/succeslogin.html", method = RequestMethod.GET)
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Long id = user.getId();
        String authToken = user.getAuthToken();
         if (user.getType() == UserType.STUDENT && user.getStatus() == UserStatus.ACTIVE) {
            return "redirect:/user-dashboard/" + authToken;
        } else if (user.getType() == UserType.PROFESOR && user.getStatus() == UserStatus.ACTIVE) {
            return "redirect:/DashboardProfessor/" + authToken;
         } else if (user.getType() == UserType.ADMIN) {
            return "redirect:/QKP85NW83DGZ2EWYXHVRJH1IDJ7SDCULSCJP460E8Z4DKQQQCROIVTGG0X1Y"
        } else {
            return "redirect:/userinactive";
        }
    }
}