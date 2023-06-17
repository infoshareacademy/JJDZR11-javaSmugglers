package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.registration.RegistrationRequest;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.service.RegistrationService;
import pl.isa.javasmugglers.web.service.UserService;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    @PostMapping("/createUser")
    public String createuser(@ModelAttribute RegistrationRequest request, HttpSession session) {

        private final RegistrationService registrationService;

        boolean f = registrationService.(request.getEmail());

        if (f) {
            session.setAttribute("msg", "Email Id alreday exists");
        }

        else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg", "Register Sucessfully");
            } else {
                session.setAttribute("msg", "Something wrong on server");
            }
        }

        return "redirect:/register";
    }

}
